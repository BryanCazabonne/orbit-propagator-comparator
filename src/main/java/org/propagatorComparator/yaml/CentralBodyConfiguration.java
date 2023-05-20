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

import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.frames.Predefined;

/**
 * Class is taken from Orekit Tutorials project developed under Apache V2.0 license.
 * Original class is named TutorialBody.
 */
/**
 * Initial data for the central body.
 * <p>
 * Data are read from a YAML file.
 * </p>
 * @author Bryan Cazabonne
 */
public class CentralBodyConfiguration {

    /** Year of the IERS Convention (1996, 2003 or 2010). */
    private int iersConventionYear;

    /** Name of the body frame related to body shape. */
    private String frameName;

    /** Equatorial radius of the body (m). */
    private double equatorialRadius;

    /** Inverse flattening of the body. */
    private double inverseFlattening;

    /**
     * Get the year of the IERS Convention (1996, 2003 or 2010).
     * @return the year of the IERS Convention
     */
    public int getIersConventionYear() {
        return iersConventionYear;
    }

    /**
     * Set the year of the IERS Convention (1996, 2003 or 2010).
     * @param iersConventionYear year of the IERS Convention
     */
    public void setIersConventionYear(final int iersConventionYear) {
        this.iersConventionYear = iersConventionYear;
    }

    /**
     * Get the name of the body frame related to body shape.
     * @return the name of the body frame related to body shape.
     */
    public String getFrameName() {
        return frameName;
    }

    /**
     * Set the name of the body frame related to body shape.
     * @param frameName name of the body frame related to body shape.
     */
    public void setFrameName(final String frameName) {
        this.frameName = frameName;
    }

    /**
     * Get the equatorial radius of the body.
     * @return the equatorial radius of the body (m)
     */
    public double getEquatorialRadius() {
        return equatorialRadius;
    }

    /**
     * Set the equatorial radius of the body.
     * @param equatorialRadius equatorial radius of the body (m)
     */
    public void setEquatorialRadius(final double equatorialRadius) {
        this.equatorialRadius = equatorialRadius;
    }

    /**
     * Get the inverse flattening of the body.
     * @return the inverse flattening of the body
     */
    public double getInverseFlattening() {
        return inverseFlattening;
    }

    /**
     * Set the inverse flattening of the body.
     * @param inverseFlattening inverse flattening of the body
     */
    public void setInverseFlattening(final double inverseFlattening) {
        this.inverseFlattening = inverseFlattening;
    }

    /**
     * Get the Earth frame.
     * <p>
     * We consider Earth frames are the frames with name starting with "ITRF".
     * </p>
     * @return the Earth frame
     */
    public Frame getEarthFrame() {

        // Get the name of the desired frame
        final String name = getFrameName();

        // check the name against predefined frames
        for (Predefined predefined : Predefined.values()) {
            if (name.equals(predefined.getName())) {
                if (predefined.toString().startsWith("ITRF") ||
                    predefined.toString().startsWith("GTOD")) {
                    return FramesFactory.getFrame(predefined);
                } else {
                    throw new IllegalArgumentException("No Earth frame: " + name);
                }
            }
        }

        // none of the frames match the name
        throw new IllegalArgumentException("Unknown frame: " + name);

    }

}
