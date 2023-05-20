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
package org.propagatorComparator.yaml;

/**
 * Class containing the inputs for the activity.
 * @author Bryan Cazabonne
 *
 */
public class OrbitComparatorInputs {

    /** Data used to initialize the initial orbit. */
    private OrbitConfiguration orbit;

    /** Data used to initialize the central body. */
    private CentralBodyConfiguration body;

    /** Numerical integrator used by the numerical orbit propagator. */
    private IntegratorConfiguration numericalIntegrator;

    /** Numerical integrator used by the DSST orbit propagator. */
    private IntegratorConfiguration dsstIntegrator;

    /** Force model data. */
    private ForceModelConfiguration forceModels;

    /** Propagation duration (days). */
    private double propagationDuration;

    /**
     * Get the propagation duration in days.
     * @return the propagation duration in days
     */
    public double getPropagationDuration() {
        return propagationDuration;
    }

    /**
     * Set the propagation duration in days.
     * @param propagationDuration the propagation duration in days to set
     */
    public void setPropagationDuration(double propagationDuration) {
        this.propagationDuration = propagationDuration;
    }

    /**
     * Get the initial orbit.
     * @return the initial orbit
     */
    public OrbitConfiguration getOrbit() {
        return orbit;
    }

    /**
     * Set the initial orbit.
     * @param orbit the initial orbit to set
     */
    public void setOrbit(OrbitConfiguration orbit) {
        this.orbit = orbit;
    }

    /**
     * Get the central body configuration.
     * @return the central body configuration
     */
    public CentralBodyConfiguration getBody() {
        return body;
    }

    /**
     * Set the central body configuration.
     * @param body the central body configuration to set
     */
    public void setBody(CentralBodyConfiguration body) {
        this.body = body;
    }

    /**
     * Get the numerical integrator configuration.
     * @return the numerical integrator configuration
     */
    public IntegratorConfiguration getNumericalIntegrator() {
        return numericalIntegrator;
    }

    /**
     * Set the numerical integrator configuration
     * @param numericalIntegrator the numerical integrator configuration to set
     */
    public void setNumericalIntegrator(IntegratorConfiguration numericalIntegrator) {
        this.numericalIntegrator = numericalIntegrator;
    }

    /**
     * Get the DSST integrator configuration.
     * @return the DSST integrator configuration
     */
    public IntegratorConfiguration getDsstIntegrator() {
        return dsstIntegrator;
    }

    /**
     * Set the DSST integrator configuration.
     * @param dsstIntegrator the DSST integrator configuration
     */
    public void setDsstIntegrator(IntegratorConfiguration dsstIntegrator) {
        this.dsstIntegrator = dsstIntegrator;
    }

    /**
     * Get the force model configuration.
     * @return the force model configuration
     */
    public ForceModelConfiguration getForceModels() {
        return forceModels;
    }

    /**
     * Set the force model configuration.
     * @param forceModels the force model configuration to set
     */
    public void setForceModels(ForceModelConfiguration forceModels) {
        this.forceModels = forceModels;
    }

    
}
