/* Copyright 2023 Bryan Cazabonne

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.propagatorComparator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.hipparchus.ode.ODEIntegrator;
import org.hipparchus.ode.nonstiff.ClassicalRungeKuttaIntegrator;
import org.hipparchus.ode.nonstiff.DormandPrince853Integrator;
import org.hipparchus.util.FastMath;
import org.orekit.bodies.CelestialBody;
import org.orekit.bodies.CelestialBodyFactory;
import org.orekit.bodies.OneAxisEllipsoid;
import org.orekit.data.DataContext;
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.orekit.forces.ForceModel;
import org.orekit.forces.drag.DragForce;
import org.orekit.forces.drag.IsotropicDrag;
import org.orekit.forces.gravity.HolmesFeatherstoneAttractionModel;
import org.orekit.forces.gravity.NewtonianAttraction;
import org.orekit.forces.gravity.SolidTides;
import org.orekit.forces.gravity.ThirdBodyAttraction;
import org.orekit.forces.gravity.potential.GravityFieldFactory;
import org.orekit.forces.gravity.potential.NormalizedSphericalHarmonicsProvider;
import org.orekit.forces.gravity.potential.SphericalHarmonicsProvider;
import org.orekit.forces.gravity.potential.UnnormalizedSphericalHarmonicsProvider;
import org.orekit.forces.radiation.IsotropicRadiationSingleCoefficient;
import org.orekit.forces.radiation.RadiationSensitive;
import org.orekit.forces.radiation.SolarRadiationPressure;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.models.earth.atmosphere.Atmosphere;
import org.orekit.models.earth.atmosphere.NRLMSISE00;
import org.orekit.models.earth.atmosphere.data.CssiSpaceWeatherData;
import org.orekit.orbits.CartesianOrbit;
import org.orekit.orbits.CircularOrbit;
import org.orekit.orbits.EquinoctialOrbit;
import org.orekit.orbits.KeplerianOrbit;
import org.orekit.orbits.Orbit;
import org.orekit.orbits.OrbitType;
import org.orekit.orbits.PositionAngle;
import org.orekit.propagation.PropagationType;
import org.orekit.propagation.SpacecraftState;
import org.orekit.propagation.analytical.tle.TLE;
import org.orekit.propagation.analytical.tle.TLEPropagator;
import org.orekit.propagation.numerical.NumericalPropagator;
import org.orekit.propagation.semianalytical.dsst.DSSTPropagator;
import org.orekit.propagation.semianalytical.dsst.forces.DSSTAtmosphericDrag;
import org.orekit.propagation.semianalytical.dsst.forces.DSSTNewtonianAttraction;
import org.orekit.propagation.semianalytical.dsst.forces.DSSTSolarRadiationPressure;
import org.orekit.propagation.semianalytical.dsst.forces.DSSTTesseral;
import org.orekit.propagation.semianalytical.dsst.forces.DSSTThirdBody;
import org.orekit.propagation.semianalytical.dsst.forces.DSSTZonal;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScalesFactory;
import org.orekit.utils.Constants;
import org.orekit.utils.IERSConventions;
import org.orekit.utils.PVCoordinates;
import org.propagatorComparator.yaml.CentralBodyConfiguration;
import org.propagatorComparator.yaml.ForceModelConfiguration;
import org.propagatorComparator.yaml.ForceModelConfiguration.DragConfiguration;
import org.propagatorComparator.yaml.ForceModelConfiguration.GravityConfiguration;
import org.propagatorComparator.yaml.ForceModelConfiguration.SolarRadiationPressureConfiguration;
import org.propagatorComparator.yaml.ForceModelConfiguration.ThirdBodyConfiguration;
import org.propagatorComparator.yaml.IntegratorConfiguration;
import org.propagatorComparator.yaml.OrbitComparatorInputs;
import org.propagatorComparator.yaml.OrbitConfiguration;
import org.propagatorComparator.yaml.OrbitTypeConfiguration;
import org.propagatorComparator.yaml.OrbitTypeConfiguration.CartesianOrbitConfiguration;
import org.propagatorComparator.yaml.OrbitTypeConfiguration.CircularOrbitConfiguration;
import org.propagatorComparator.yaml.OrbitTypeConfiguration.EquinoctialOrbitConfiguration;
import org.propagatorComparator.yaml.OrbitTypeConfiguration.KeplerianOrbitConfiguration;
import org.propagatorComparator.yaml.OrbitTypeConfiguration.TLEConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * Main class for orbit propagation model comparison
 * @author Bryan Cazabonne
 *
 */
public class OrbitComparator {

    /** Null double values are equal to 0.0 in YAML file. */
    private static final double NULL_DOUBLE = 0.0;

    /** Key to print the wall clock run time. */
    private static final String WALL_CLOCK_RUN_TIME = "wall clock run time (s): ";

    /**
     * Main method
     * @param args [0] is the input YAML file name
     * @throws URISyntaxException if this URL is not formatted strictly according to RFC2396 and cannot be converted to a URI.
     * @throws IOException if input YAML file cannot be read properly
     */
    public static void main(String[] args) throws URISyntaxException, IOException {

        // Initialize orekit data
        initializeOrekit();

        // Input in tutorial resources directory
        final String inputPath = OrbitComparator.class.getClassLoader().getResource(args[0]).toURI().getPath();

        // Run the program
        new OrbitComparator().run(new File(inputPath));

    }

    /**
     * Run the program.
     * @param input input file
     * @throws IOException if input YAML file cannot be read properly
     * @throws URISyntaxException if URI syntax of SP3 file is wrong
     */
    private void run(final File input) throws URISyntaxException, IOException {

        // Read input parameters
        System.out.println("Read inputs in: " + input.getAbsolutePath());
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        final OrbitComparatorInputs inputData = mapper.readValue(input, OrbitComparatorInputs.class);
        System.out.println("Read inputs done!");

        // Propagation time
        final double propagationTime = inputData.getPropagationDuration();

        // Central body
        final OneAxisEllipsoid centralBody = initializeBody(inputData);

        // Gravity field
        final NormalizedSphericalHarmonicsProvider   normalizedGravityField   = initializeNormalizedGravityField(inputData);
        final UnnormalizedSphericalHarmonicsProvider unnormalizedGravityField = initializeUnnormalizedGravityField(inputData);
        
        // Initial orbit
        final Orbit initialOrbit = initializeOrbit(inputData, normalizedGravityField);

        // Numerical propagator integrator
        final ODEIntegrator numericalIntegrator = initializeIntegrator(inputData.getNumericalIntegrator(), initialOrbit, "numerical propagator");
        // Numerical propagator
        final NumericalPropagator numericalPropagator = initializeNumericalPropagator(inputData, numericalIntegrator, initialOrbit, centralBody, normalizedGravityField);

        // DSST propagator integrator
        final ODEIntegrator dsstIntegrator = initializeIntegrator(inputData.getDsstIntegrator(), initialOrbit, "DSST propagator");
        // DSST propagator
        final DSSTPropagator dsstPropagator = initializeDSSTPropagator(inputData, dsstIntegrator, initialOrbit, centralBody, unnormalizedGravityField);

        // Build the numerical propagator and propagate
        final double t0Num = System.currentTimeMillis();
        final SpacecraftState numericalState = numericalPropagator.propagate(initialOrbit.getDate().shiftedBy(propagationTime * Constants.JULIAN_DAY));
        final double t1Num = System.currentTimeMillis();
        System.out.println("");
        System.out.println("Numerical " + WALL_CLOCK_RUN_TIME + (0.001 * (t1Num - t0Num)));
        System.out.println(numericalState);

        // Build the numerical propagator and propagate
        final double t0Dsst = System.currentTimeMillis();
        final SpacecraftState dsstState = dsstPropagator.propagate(initialOrbit.getDate().shiftedBy(propagationTime * Constants.JULIAN_DAY));
        final double t1Dsst = System.currentTimeMillis();
        System.out.println("");
        System.out.println("DSST " + WALL_CLOCK_RUN_TIME + (0.001 * (t1Dsst - t0Dsst)));
        System.out.println(dsstState);

    }

    /**
     * Initialize the central body (i.e. the Earth).
     * @param inputData input data
     * @return a configured central body
     */
    private static OneAxisEllipsoid initializeBody(final OrbitComparatorInputs inputData) {

        // Body data
        final CentralBodyConfiguration body = inputData.getBody();

        // Body Frame
        final Frame bodyFrame;
        if (body.getFrameName() != null) {
            bodyFrame = body.getEarthFrame();
        } else {
            bodyFrame = FramesFactory.getITRF(IERSConventions.IERS_2010, true);
        }

        // Equatorial radius
        final double equatorialRadius;
        if (body.getEquatorialRadius() != NULL_DOUBLE) {
            equatorialRadius = body.getEquatorialRadius();
        } else {
            equatorialRadius = Constants.WGS84_EARTH_EQUATORIAL_RADIUS;
        }

        // Flattening
        final double flattening;
        if (body.getInverseFlattening() != NULL_DOUBLE) {
            flattening = 1.0 / body.getInverseFlattening();
        } else {
            flattening = Constants.WGS84_EARTH_FLATTENING;
        }

        // Return the configured body
        System.out.println("");
        System.out.println("   Central body          ");
        System.out.println("      Equatorial radius: " + equatorialRadius);
        System.out.println("      Flattening:        " + flattening);
        System.out.println("      Body frame:        " + bodyFrame.toString());
        return new OneAxisEllipsoid(equatorialRadius, flattening, bodyFrame);

    }

    /**
     * Initialize the normalized spherical harmonics provider.
     * @param inputData input data
     * @return a configured spherical harmonics provider
     */
    private static NormalizedSphericalHarmonicsProvider initializeNormalizedGravityField(final OrbitComparatorInputs inputData) {

        // Gravity data
        final GravityConfiguration gravityData = inputData.getForceModels().getGravity();

        // Use input data
        final int degree = gravityData.getDegree();
        final int order  = FastMath.min(degree, gravityData.getOrder());
        
        // Return
        return GravityFieldFactory.getNormalizedProvider(degree, order);

    }

    /**
     * Initialize the unnormalized spherical harmonics provider.
     * @param inputData input data
     * @return a configured spherical harmonics provider
     */
    private static UnnormalizedSphericalHarmonicsProvider initializeUnnormalizedGravityField(final OrbitComparatorInputs inputData) {

        // Gravity data
        final GravityConfiguration gravityData = inputData.getForceModels().getGravity();

        // Use input data
        final int degree = gravityData.getDegree();
        final int order  = FastMath.min(degree, gravityData.getOrder());
        
        // Return
        return GravityFieldFactory.getUnnormalizedProvider(degree, order);

    }

    /**
     * Initialize initial orbit.
     * @param inputData tutorial input data
     * @param gravityField gravity field (used for the central attraction coefficient)
     * @return the configured orbit
     */
    private static Orbit initializeOrbit(final OrbitComparatorInputs inputData,
                                         final SphericalHarmonicsProvider gravityField) {

        // Input orbit data
        final OrbitConfiguration orbit = inputData.getOrbit();

        // Central attration coefficient
        final double mu = gravityField.getMu();

        // Verify if an input orbit has been defined
        // If not, we use the first orbit entry in the CPF file
        if (orbit != null) {

            // Input orbit data were defined
            final OrbitTypeConfiguration data = orbit.getOrbitType();

            // Inertial frame
            final Frame frame = orbit.getInertialFrame() == null ? FramesFactory.getEME2000() : orbit.getInertialFrame();

            // Obit definition
            if (data.getKeplerian() != null) {
                // Input data are Keplerian elements
                final KeplerianOrbitConfiguration keplerian = data.getKeplerian();
                return new KeplerianOrbit(keplerian.getA(), keplerian.getE(),
                                          FastMath.toRadians(keplerian.getI()),
                                          FastMath.toRadians(keplerian.getPa()),
                                          FastMath.toRadians(keplerian.getRaan()),
                                          FastMath.toRadians(keplerian.getV()),
                                          PositionAngle.valueOf(keplerian.getPositionAngle()),
                                          frame,
                                          new AbsoluteDate(orbit.getDate(),
                                                         TimeScalesFactory.getUTC()),
                                          mu);
            } else if (data.getEquinoctial() != null) {
                // Input data are Equinoctial elements
                final EquinoctialOrbitConfiguration equinoctial = data.getEquinoctial();
                return new EquinoctialOrbit(equinoctial.getA(),
                                            equinoctial.getEx(), equinoctial.getEy(),
                                            equinoctial.getHx(), equinoctial.getHy(),
                                            FastMath.toRadians(equinoctial.getLv()),
                                            PositionAngle.valueOf(equinoctial.getPositionAngle()),
                                            frame,
                                            new AbsoluteDate(orbit.getDate(),
                                                             TimeScalesFactory.getUTC()),
                                            mu);
            } else if (data.getCircular() != null) {
                // Input data are Circular elements
                final CircularOrbitConfiguration circular = data.getCircular();
                return new CircularOrbit(circular.getA(),
                                         circular.getEx(), circular.getEy(),
                                         FastMath.toRadians(circular.getI()),
                                         FastMath.toRadians(circular.getRaan()),
                                         FastMath.toRadians(circular.getAlphaV()),
                                         PositionAngle.valueOf(circular.getPositionAngle()),
                                         frame,
                                         new AbsoluteDate(orbit.getDate(),
                                                          TimeScalesFactory.getUTC()),
                                         mu);
            } else if (data.getTle() != null) {
                // Input data is a TLE
                final TLEConfiguration tleData = data.getTle();
                final String line1 = tleData.getLine1();
                final String line2 = tleData.getLine2();
                final TLE tle = new TLE(line1, line2);

                final TLEPropagator propagator = TLEPropagator.selectExtrapolator(tle);

                final AbsoluteDate initDate = tle.getDate();
                final SpacecraftState initialState = propagator.getInitialState();

                //Transformation from TEME to frame.
                return new CartesianOrbit(initialState.getPVCoordinates(frame),
                                          frame, initDate, mu);

            } else {
                // Input data are Cartesian elements
                final CartesianOrbitConfiguration cartesian = data.getCartesian();
                final double[] pos = {cartesian.getX(), cartesian.getY(), cartesian.getZ()};
                final double[] vel = {cartesian.getVx(), cartesian.getVy(), cartesian.getVz()};

                return new CartesianOrbit(new PVCoordinates(new Vector3D(pos), new Vector3D(vel)),
                                          frame,
                                          new AbsoluteDate(orbit.getDate(),
                                                           TimeScalesFactory.getUTC()),
                                          mu);
            }

        } else {

            // Orbit must be defined
            throw new IllegalArgumentException("Orbit must be defined!");

        }

    }

    /**
     * Initialize integrator data.
     * <p>
     * Can be null for analytical orbit determination.
     * </p>
     * @param inputData input data
     * @param orbit initial orbit
     * @param propagatorName name of the orbit propagator
     * @return a configured integrator
     */
    private static ODEIntegrator initializeIntegrator(final IntegratorConfiguration integratorData, final Orbit initialOrbit,
                                                      final String propagatorName) {

        if (integratorData != null) {

            System.out.println("");
            System.out.println("   Integrator data for: " + propagatorName);

            // Fixed step
            if (integratorData.getFixedStep() != NULL_DOUBLE) {

                // RK4 integrator
                System.out.println("      RK 4th order   ");
                System.out.println("      Fixed step:        " + integratorData.getFixedStep());
                return new ClassicalRungeKuttaIntegrator(integratorData.getFixedStep());

            } else {

                // Variable step integrator
                System.out.println("      Dormand-Prince 8(5,3)");
                System.out.println("      Min step:          " + integratorData.getMinStep());
                System.out.println("      Max step:          " + integratorData.getMaxStep());
                System.out.println("      Position error:    " + integratorData.getPositionError());
                final double[][] tolerances =
                                NumericalPropagator.tolerances(integratorData.getPositionError(), initialOrbit, OrbitType.EQUINOCTIAL);
                return new DormandPrince853Integrator(integratorData.getMinStep(),
                                                      integratorData.getMaxStep(),
                                                      tolerances[0], tolerances[1]);

            }

        } else {

            // Integrator shall be defined
            throw new IllegalArgumentException("Integrator shall be defined for: " + propagatorName);

        }

    }

    /**
     * Initialize the propagator builder.
     * @param inputData input data
     * @param integrator integrator builder
     * @param orbit initial guess
     * @param centralBody central body
     * @param gravityField gravity field
     * @return a configured propagator builder
     */
    private static NumericalPropagator initializeNumericalPropagator(final OrbitComparatorInputs inputData,
                                                                     final ODEIntegrator integrator,
                                                                     final Orbit orbit,
                                                                     final OneAxisEllipsoid centralBody,
                                                                     final NormalizedSphericalHarmonicsProvider gravityField) {

        // Force model configuration
        final ForceModelConfiguration forceModels = inputData.getForceModels();

        System.out.println("");
        System.out.println("   Numerical model       ");
        // Initialize the numerical builder
        final NumericalPropagator numPropagator = new NumericalPropagator(integrator);
        numPropagator.setOrbitType(OrbitType.EQUINOCTIAL);

        // Add force models to the numerical propagator
        addNumericalForceModels(forceModels, numPropagator, centralBody, gravityField);

        // Update
        numPropagator.setInitialState(new SpacecraftState(orbit));

        // Return
        return numPropagator;

    }

    /**
     * Initialize the DSST propagator builder.
     * @param inputData input data
     * @param integrator integrator builder
     * @param orbit initial guess
     * @param centralBody central body
     * @param gravityField gravity field
     * @return a configured propagator builder
     */
    private static DSSTPropagator initializeDSSTPropagator(final OrbitComparatorInputs inputData,
                                                           final ODEIntegrator integrator,
                                                           final Orbit orbit,
                                                           final OneAxisEllipsoid centralBody,
                                                           final UnnormalizedSphericalHarmonicsProvider gravityField) {

        // Force model configuration
        final ForceModelConfiguration forceModels = inputData.getForceModels();

        System.out.println("");
        System.out.println("   DSST model            ");
        // Initialize the numerical builder
        final DSSTPropagator dsstPropagator = new DSSTPropagator(integrator, PropagationType.OSCULATING);

        // Add force models to the DSST propagator
        addDSSTForceModels(forceModels, dsstPropagator, centralBody, gravityField);

        // Update
        dsstPropagator.setInitialState(new SpacecraftState(orbit), PropagationType.OSCULATING);
        dsstPropagator.setInterpolationGridToMaxTimeGap(86400.0);

        // Return
        return dsstPropagator;

    }

    /**
     * Add the force models to the numerical propagator.
     * @param forceModelData force model data
     * @param propagator propagator
     * @param centralBody central body
     * @param gravityField gravity field
     */
    private static void addNumericalForceModels(final ForceModelConfiguration forceModelData,
                                                final NumericalPropagator propagator,
                                                final OneAxisEllipsoid centralBody,
                                                final NormalizedSphericalHarmonicsProvider gravityField) {

        // Drag
        if (forceModelData.getDrag() != null) {

            System.out.println("      Adding drag        ");
            // Drag data
            final DragConfiguration drag   = forceModelData.getDrag();
            final double       cd          = drag.getCd();
            final double       area        = drag.getArea();

            System.out.println("         Cd value:       " + cd);
            System.out.println("         Area:           " + area);

            // Atmosphere model
            final CssiSpaceWeatherData cswd = new CssiSpaceWeatherData(CssiSpaceWeatherData.DEFAULT_SUPPORTED_NAMES);
            final Atmosphere atmosphere = new NRLMSISE00(cswd, CelestialBodyFactory.getSun(), centralBody);
            System.out.println("         Atmosphere:     " + "NRLMSISE00");

            // Add the force model
            final ForceModel force = new DragForce(atmosphere, new IsotropicDrag(area, cd));
            propagator.addForceModel(force);

        }

        // Third bodies
        final List<CelestialBody> solidTidesBodies = new ArrayList<>();
        if (forceModelData.getThirdBody() != null) {
            for (ThirdBodyConfiguration thirdBody : forceModelData.getThirdBody()) {
                final CelestialBody body = CelestialBodyFactory.getBody(thirdBody.getName());
                System.out.println("      Adding 3rd body:   " + body.getName());
                propagator.addForceModel(new ThirdBodyAttraction(body));
                if (thirdBody.isWithSolidTides()) {
                    System.out.println("        With tides:      true");
                    System.out.println("        Ephemeris:       " + DataContext.getDefault().getDataProvidersManager().getLoadedDataNames().toArray()[4]);
                    solidTidesBodies.add(body);
                }
                

            }
        }

        // Solid tides
        if (!solidTidesBodies.isEmpty()) {
            propagator.addForceModel(new SolidTides(centralBody.getBodyFrame(),
                                                    gravityField.getAe(), gravityField.getMu(),
                                                    gravityField.getTideSystem(), IERSConventions.IERS_2010,
                                                    TimeScalesFactory.getUT1(IERSConventions.IERS_2010, true),
                                                    solidTidesBodies.toArray(new CelestialBody[solidTidesBodies.size()])));
        }

        // Solar radiation pressure
        if (forceModelData.getSolarRadiationPressure() != null) {

            System.out.println("      Adding SRP         ");

            // Solar radiation pressure data
            final SolarRadiationPressureConfiguration srp = forceModelData.getSolarRadiationPressure();
            final double  cr                         = srp.getCr();
            final double  area                       = srp.getArea();

            System.out.println("         Cr value:       " + cr);
            System.out.println("         Area:           " + area);

            // Satellite model (spherical)
            final RadiationSensitive spacecraft = new IsotropicRadiationSingleCoefficient(area, cr);

            // Solar radiation pressure
            final SolarRadiationPressure force = new SolarRadiationPressure(CelestialBodyFactory.getSun(), gravityField.getAe(), spacecraft);
            //force.addOccultingBody(CelestialBodyFactory.getMoon(), Constants.MOON_EQUATORIAL_RADIUS);

            // Add the force model
            propagator.addForceModel(force);

        }

        // Potential
        if (forceModelData.getGravity().getDegree() != 0) {
            System.out.println("      Adding Earth harmonics");
            System.out.println("         Degree:         " + gravityField.getMaxDegree());
            System.out.println("         Order:          " + gravityField.getMaxOrder());
            System.out.println("         Mu:             " + gravityField.getMu());
            System.out.println("         Model:          " + "Holmes-Featherstone");
            System.out.println("         Gravity file:   " + DataContext.getDefault().getDataProvidersManager().getLoadedDataNames().toArray()[3]);
            propagator.addForceModel(new HolmesFeatherstoneAttractionModel(centralBody.getBodyFrame(), (NormalizedSphericalHarmonicsProvider) gravityField));
        }

        // Newton
        propagator.addForceModel(new NewtonianAttraction(gravityField.getMu()));

    }

    /**
     * Add the force models to the DSST propagator.
     * @param forceModelData force model data
     * @param propagator propagator
     * @param centralBody central body
     * @param gravityField gravity field
     */
    private static void addDSSTForceModels(final ForceModelConfiguration forceModelData,
                                           final DSSTPropagator propagator,
                                           final OneAxisEllipsoid centralBody,
                                           final UnnormalizedSphericalHarmonicsProvider gravityField) {

        // Drag
        if (forceModelData.getDrag() != null) {

            System.out.println("      Adding drag        ");

            // Drag data
            final DragConfiguration drag   = forceModelData.getDrag();
            final double       cd          = drag.getCd();
            final double       area        = drag.getArea();

            System.out.println("         Cd value:       " + cd);
            System.out.println("         Area:           " + area);

            // Atmosphere model
            final CssiSpaceWeatherData cswd = new CssiSpaceWeatherData(CssiSpaceWeatherData.DEFAULT_SUPPORTED_NAMES);
            final Atmosphere atmosphere = new NRLMSISE00(cswd, CelestialBodyFactory.getSun(), centralBody);
            System.out.println("         Atmosphere:     " + "NRLMSISE00");

            // Drag force - Assuming spherical satellite
            // Add the force model
            propagator.addForceModel(new DSSTAtmosphericDrag(new DragForce(atmosphere, new IsotropicDrag(area, cd)), gravityField.getMu()));

        }

        // Solar radiation pressure
        if (forceModelData.getSolarRadiationPressure() != null) {

            System.out.println("      Adding SRP         ");

            // Solar radiation pressure data
            final SolarRadiationPressureConfiguration srp = forceModelData.getSolarRadiationPressure();
            final double  cr                              = srp.getCr();
            final double  area                            = srp.getArea();

            System.out.println("         Cr value:       " + cr);
            System.out.println("         Area:           " + area);

            // Satellite model (spherical)
            final RadiationSensitive spacecraft = new IsotropicRadiationSingleCoefficient(area, cr);

            // Solar radiation pressure
            propagator.addForceModel(new DSSTSolarRadiationPressure(CelestialBodyFactory.getSun(), gravityField.getAe(), spacecraft, gravityField.getMu()));

        }

        // Third bodies
        for (ThirdBodyConfiguration thirdBody : forceModelData.getThirdBody()) {
            final CelestialBody body = CelestialBodyFactory.getBody(thirdBody.getName());
            System.out.println("      Adding 3rd body:   " + body.getName());
            propagator.addForceModel(new DSSTThirdBody(body, gravityField.getMu()));
            System.out.println("        With tides:      false");
        }

        // Potential
        System.out.println("      Adding Earth harmonics");
        System.out.println("         Degree:         " + gravityField.getMaxDegree());
        System.out.println("         Order:          " + gravityField.getMaxOrder());
        System.out.println("         Mu:             " + gravityField.getMu());
        propagator.addForceModel(new DSSTTesseral(centralBody.getBodyFrame(), Constants.WGS84_EARTH_ANGULAR_VELOCITY, gravityField));
        propagator.addForceModel(new DSSTZonal(gravityField));

        // Newton
        propagator.addForceModel(new DSSTNewtonianAttraction(gravityField.getMu()));

    }

    /**
     * Initialize Orekit data.
     * @return home directory
     */
    private static File initializeOrekit() {
        // Configure path
        final File home       = new File(System.getProperty("user.home"));
        final File orekitData = new File(home, "orekit-data");
        // Define data provider
        final DataProvidersManager manager = DataContext.getDefault().getDataProvidersManager();
        manager.addProvider(new DirectoryCrawler(orekitData));
        return home;
    }

}
