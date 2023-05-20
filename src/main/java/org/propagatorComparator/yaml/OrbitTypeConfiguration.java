/* Copyright 2002-2023 CS GROUP
 * Licensed to CS GROUP (CS) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * CS licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.propagatorComparator.yaml;

import org.orekit.orbits.OrbitType;

/**
 * Class is taken from Orekit Tutorials project developed under Apache V2.0 license.
 * Original class is named TutorialOrbitType.
 */
/**
 * Input data for tutorial orbit type.
 * @author Bryan Cazabonne
 */
public class OrbitTypeConfiguration {

    /** Name of the orbit type. */
    private String name;

    /** Cartesian orbit elements. */
    private CartesianOrbitConfiguration cartesian;

    /** Keplerian orbit elements. */
    private KeplerianOrbitConfiguration keplerian;

    /** Equinoctial orbit elements. */
    private EquinoctialOrbitConfiguration equinoctial;

    /** Circular orbit elements. */
    private CircularOrbitConfiguration circular;

    /** TLE data. */
    private TLEConfiguration tle;

    /**
     * Get the name of the orbit type.
     * @return the name of the orbit type
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the orbit type.
     * @param name name of the orbit type
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get the cartesian elements.
     * @return the cartesian elements
     */
    public CartesianOrbitConfiguration getCartesian() {
        return cartesian;
    }

    /**
     * Set the cartesian elements.
     * @param cartesian cartesian elements
     */
    public void setCartesian(final CartesianOrbitConfiguration cartesian) {
        this.cartesian = cartesian;
    }

    /**
     * Get the keplerian elements.
     * @return the keplerian elements
     */
    public KeplerianOrbitConfiguration getKeplerian() {
        return keplerian;
    }

    /**
     * Set the keplerian elements.
     * @param keplerian keplerian elements
     */
    public void setKeplerian(final KeplerianOrbitConfiguration keplerian) {
        this.keplerian = keplerian;
    }

    /**
     * Get the equinoctial elements.
     * @return the equinoctial elements
     */
    public EquinoctialOrbitConfiguration getEquinoctial() {
        return equinoctial;
    }

    /**
     * Set the equinoctial elements.
     * @param equinoctial equinoctial elements
     */
    public void setEquinoctial(final EquinoctialOrbitConfiguration equinoctial) {
        this.equinoctial = equinoctial;
    }

    /**
     * Get the circular elements.
     * @return the circular elements
     */
    public CircularOrbitConfiguration getCircular() {
        return circular;
    }

    /**
     * Set the circular elements.
     * @param circular circular elements
     */
    public void setCircular(final CircularOrbitConfiguration circular) {
        this.circular = circular;
    }

    /**
     * Get the TLE data.
     * @return the TLE data
     */
    public TLEConfiguration getTle() {
        return tle;
    }

    /**
     * Set the TLE data.
     * @param tle TLE data
     */
    public void setTle(final TLEConfiguration tle) {
        this.tle = tle;
    }

    /**
     * Get the orbit type.
     * @return the orbit type
     */
    public OrbitType getOrbitType() {
        // Get the name of the orbit type
        final String orbitType = getName();
        return orbitType == null ? null : OrbitType.valueOf(orbitType);
    }

    /** Cartesian orbit. */
    public static class CartesianOrbitConfiguration {

        /** First coordinate of position vector (m). */
        private double x;

        /** Second coordinate of position vector (m). */
        private double y;

        /** Third coordinate of position vector (m). */
        private double z;

        /** First coordinate of velocity vector (m/s). */
        private double vx;

        /** Second coordinate of velocity vector (m/s). */
        private double vy;

        /** Third coordinate of velocity vector (m/s). */
        private double vz;

        /**
         * Get the first coordinate of position vector.
         * @return the first coordinate of position vector (m)
         */
        public double getX() {
            return x;
        }

        /**
         * Set the first coordinate of position vector.
         * @param x first coordinate of position vector (m)
         */
        public void setX(final double x) {
            this.x = x;
        }

        /**
         * Get the second coordinate of position vector.
         * @return the second coordinate of position vector (m)
         */
        public double getY() {
            return y;
        }

        /**
         * Set the second coordinate of position vector.
         * @param y second coordinate of position vector (m)
         */
        public void setY(final double y) {
            this.y = y;
        }

        /**
         * Get the third coordinate of the satellite position.
         * @return the third coordinate of the satellite position (m)
         */
        public double getZ() {
            return z;
        }

        /**
         * Set the third coordinate of the satellite position.
         * @param z third coordinate of the satellite position (m)
         */
        public void setZ(final double z) {
            this.z = z;
        }

        /**
         * Get the first coordinate of the satellite velocity.
         * @return the first coordinate of the satellite velocity (m/s)
         */
        public double getVx() {
            return vx;
        }

        /**
         * Set the first coordinate of the satellite velocity.
         * @param vx first coordinate of the satellite velocity (m/s)
         */
        public void setVx(final double vx) {
            this.vx = vx;
        }

        /**
         * Get the second coordinate of the satellite velocity.
         * @return the second coordinate of the satellite velocity (m/s)
         */
        public double getVy() {
            return vy;
        }

        /**
         * Set the second coordinate of the satellite velocity.
         * @param vy second coordinate of the satellite velocity (m/s)
         */
        public void setVy(final double vy) {
            this.vy = vy;
        }

        /**
         * Get the third coordinate of the satellite velocity.
         * @return the third coordinate of the satellite velocity (m/s)
         */
        public double getVz() {
            return vz;
        }

        /**
         * Set the third coordinate of the satellite velocity.
         * @param vz third coordinate of the satellite velocity (m/s)
         */
        public void setVz(final double vz) {
            this.vz = vz;
        }

    }

    /** Keplerian orbit. */
    public static class KeplerianOrbitConfiguration {

        /** Semi-major axis (m). */
        private double a;

        /** Eccentricity. */
        private double e;

        /** Inclination (°). */
        private double i;

        /** Perigee Argument (°). */
        private double pa;

        /** Right Ascension of Ascending Node (°). */
        private double raan;

        /** True anomaly (°). */
        private double v;

        /** Type of anomaly. */
        private String positionAngle;

        /**
         * Get the semi major axis.
         * @return the semi major axis (m)
         */
        public double getA() {
            return a;
        }

        /**
         * Set the semi major axis.
         * @param a semi major axis (m)
         */
        public void setA(final double a) {
            this.a = a;
        }

        /**
         * Get the orbit eccentricity.
         * @return the orbit eccentricity
         */
        public double getE() {
            return e;
        }

        /**
         * Set the orbit eccentricity.
         * @param e orbit eccentricity
         */
        public void setE(final double e) {
            this.e = e;
        }

        /**
         * Get the orbit inclination.
         * @return the orbit inclination (°)
         */
        public double getI() {
            return i;
        }

        /**
         * Set the orbit inclination.
         * @param i orbit inclination (°)
         */
        public void setI(final double i) {
            this.i = i;
        }

        /**
         * Get the perigee argument.
         * @return the perigee argument (°)
         */
        public double getPa() {
            return pa;
        }

        /**
         * Set the perigee argument.
         * @param pa perigee argument (°)
         */
        public void setPa(final double pa) {
            this.pa = pa;
        }

        /**
         * Get the right ascension of the ascending node.
         * @return the right ascension of the ascending node (°)
         */
        public double getRaan() {
            return raan;
        }

        /**
         * Set the right ascension of the ascending node.
         * @param raan right ascension of the ascending node (°)
         */
        public void setRaan(final double raan) {
            this.raan = raan;
        }

        /**
         * Get the true anomaly.
         * @return the true anomaly (°)
         */
        public double getV() {
            return v;
        }

        /**
         * Set the true anomaly.
         * @param v true anomaly (°)
         */
        public void setV(final double v) {
            this.v = v;
        }

        /**
         * Get the name of the position angle.
         * @return the name of the position angle
         */
        public String getPositionAngle() {
            return positionAngle;
        }

        /**
         * Set the name of the position angle.
         * @param positionAngle name of the position angle
         */
        public void setPositionAngle(final String positionAngle) {
            this.positionAngle = positionAngle;
        }

    }

    /** Equinoctial orbit. */
    public static class EquinoctialOrbitConfiguration {

        /** Semi-major axis (m). */
        private double a;

        /** First component of the eccentricity vector. */
        private double ex;

        /** Second component of the eccentricity vector. */
        private double ey;

        /** First component of the inclination vector. */
        private double hx;

        /** Second component of the inclination vector. */
        private double hy;

        /** True longitude argument (°). */
        private double lv;

        /** Type of anomaly. */
        private String positionAngle;

        /**
         * Get the semi major axis.
         * @return the semi major axis (m)
         */
        public double getA() {
            return a;
        }

        /**
         * Set the semi major axis.
         * @param a the semi major axis (m)
         */
        public void setA(final double a) {
            this.a = a;
        }

        /**
         * Get the first component of the equinoctial eccentricity vector.
         * @return the first component of the equinoctial eccentricity vector
         */
        public double getEx() {
            return ex;
        }

        /**
         * Set the first component of the equinoctial eccentricity vector.
         * @param ex first component of the equinoctial eccentricity vector
         */
        public void setEx(final double ex) {
            this.ex = ex;
        }

        /** Get the second component of the equinoctial eccentricity vector.
         * @return the second component of the equinoctial eccentricity vector
         */
        public double getEy() {
            return ey;
        }

        /**
         * Set the second component of the equinoctial eccentricity vector.
         * @param ey second component of the equinoctial eccentricity vector
         */
        public void setEy(final double ey) {
            this.ey = ey;
        }

        /**
         * Get the first component of the inclination vector.
         * @return the first component of the inclination vector
         */
        public double getHx() {
            return hx;
        }

        /**
         * Set the first component of the inclination vector.
         * @param hx first component of the inclination vector
         */
        public void setHx(final double hx) {
            this.hx = hx;
        }

        /**
         * Get the second component of the inclination vector.
         * @return the second component of the inclination vector
         */
        public double getHy() {
            return hy;
        }

        /**
         * Set the second component of the inclination vector.
         * @param hy second component of the inclination vector
         */
        public void setHy(final double hy) {
            this.hy = hy;
        }

        /**
         * Get the mean anomaly.
         * @return the mean anomaly (°)
         */
        public double getLv() {
            return lv;
        }

        /**
         * Set the mean anomaly.
         * @param lv mean anomaly (°)
         */
        public void setLv(final double lv) {
            this.lv = lv;
        }

        /**
         * Get the name of the position angle.
         * @return the name of the position angle
         */
        public String getPositionAngle() {
            return positionAngle;
        }

        /**
         * Set the name of the position angle.
         * @param positionAngle name of the position angle
         */
        public void setPositionAngle(final String positionAngle) {
            this.positionAngle = positionAngle;
        }

    }

    /** Circular orbit. */
    public static class CircularOrbitConfiguration {

        /** Semi-major axis (m). */
        private double a;

        /** First component of the circular eccentricity vector. */
        private double ex;

        /** Second component of the circular eccentricity vector. */
        private double ey;

        /** Inclination (°). */
        private double i;

        /** Right Ascension of Ascending Node (°). */
        private double raan;

        /** True latitude argument (°). */
        private double alphaV;

        /** Type of anomaly. */
        private String positionAngle;

        /**
         * Get the semi major axis.
         * @return the semi major axis (m)
         */
        public double getA() {
            return a;
        }

        /**
         * Set the semi major axis.
         * @param a the semi major axis (m)
         */
        public void setA(final double a) {
            this.a = a;
        }

        /**
         * Get the first component of the equinoctial eccentricity vector.
         * @return the first component of the equinoctial eccentricity vector
         */
        public double getEx() {
            return ex;
        }

        /**
         * Set the first component of the equinoctial eccentricity vector.
         * @param ex first component of the equinoctial eccentricity vector
         */
        public void setEx(final double ex) {
            this.ex = ex;
        }

        /** Get the second component of the equinoctial eccentricity vector.
         * @return the second component of the equinoctial eccentricity vector
         */
        public double getEy() {
            return ey;
        }

        /**
         * Set the second component of the equinoctial eccentricity vector.
         * @param ey second component of the equinoctial eccentricity vector
         */
        public void setEy(final double ey) {
            this.ey = ey;
        }

        /**
         * Get the orbit inclination.
         * @return the orbit inclination (°)
         */
        public double getI() {
            return i;
        }

        /**
         * Set the orbit inclination.
         * @param i orbit inclination (°)
         */
        public void setI(final double i) {
            this.i = i;
        }

        /**
         * Get the right ascension of the ascending node.
         * @return the right ascension of the ascending node (°)
         */
        public double getRaan() {
            return raan;
        }

        /**
         * Set the right ascension of the ascending node.
         * @param raan right ascension of the ascending node (°)
         */
        public void setRaan(final double raan) {
            this.raan = raan;
        }

        /**
         * Get the true latitude argument.
         * @return v + ω true latitude argument (°)
         */
        public double getAlphaV() {
            return alphaV;
        }

        /**
         * Set the true latitude argument.
         * @param alphaV true latitude argument (°)
         */
        public void setAlphaV(final double alphaV) {
            this.alphaV = alphaV;
        }

        /**
         * Get the name of the position angle.
         * @return the name of the position angle
         */
        public String getPositionAngle() {
            return positionAngle;
        }

        /**
         * Set the name of the position angle.
         * @param positionAngle name of the position angle
         */
        public void setPositionAngle(final String positionAngle) {
            this.positionAngle = positionAngle;
        }

    }

    /** TLE data. */
    public static class TLEConfiguration {

        /** First line of TLE data. */
        private String line1;

        /** Second line of TLE data. */
        private String line2;

        /**
         * Get the TLE first line.
         * @return the TLE first line
         */
        public String getLine1() {
            return line1;
        }

        /**
         * Set the TLE first line.
         * @param line1 TLE first line
         */
        public void setLine1(final String line1) {
            this.line1 = line1;
        }

        /**
         * Get the TLE second line.
         * @return the TLE second line
         */
        public String getLine2() {
            return line2;
        }

        /**
         * Set the TLE second line.
         * @param line2 TLE second line
         */
        public void setLine2(final String line2) {
            this.line2 = line2;
        }

    }

}
