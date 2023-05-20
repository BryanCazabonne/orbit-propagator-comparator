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

import org.orekit.errors.OrekitException;
import org.orekit.errors.OrekitMessages;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.frames.Predefined;

/**
 * Class is taken from Orekit Tutorials project developed under Apache V2.0 license.
 * Original class is named TutorialOrbit.
 */
/**
 * Initial data for the orbit determination orbit.
 * <p>
 * Data are read from a YAML file.
 * </p>
 * @author Bryan Cazabonne
 */
public class OrbitConfiguration {

    /** String representation of the orbit date. */
    private String date;

    /** Name of the inertial orbit frame. */
    private String frameName;

    /** Orbit type (name and orbital elements). */
    private OrbitTypeConfiguration orbitType;

    /**
     * Get the String representation of the orbit date.
     * @return the String representation of the orbit date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the String representation of the orbit date.
     * @param date String representation of the orbit date
     */
    public void setDate(final String date) {
        this.date = date;
    }

    /**
     * Get the name of the inertial orbit frame.
     * @return the name of the inertial orbit frame
     */
    public String getFrameName() {
        return frameName;
    }

    /**
     * Set the name of the inertial orbit frame.
     * @param frameName name of the inertial orbit frame
     */
    public void setFrameName(final String frameName) {
        this.frameName = frameName;
    }

    /**
     * Get the orbit type (name and orbital elements).
     * @return the orbit type including the name and the orbital elements
     */
    public OrbitTypeConfiguration getOrbitType() {
        return orbitType;
    }

    /**
     * Set the orbit type (name and orbital elements).
     * @param orbitType orbit type including the name and the orbital elements
     */
    public void setOrbitType(final OrbitTypeConfiguration orbitType) {
        this.orbitType = orbitType;
    }

    /**
     * Get the inertial frame.
     * @return the inertial frame
     */
    public Frame getInertialFrame() {

        // Get the name of the desired frame
        final String name = getFrameName();

        // Check the name against predefined frames
        for (Predefined predefined : Predefined.values()) {
            if (name.equals(predefined.getName())) {
                if (FramesFactory.getFrame(predefined).isPseudoInertial()) {
                    return FramesFactory.getFrame(predefined);
                } else {
                    throw new OrekitException(OrekitMessages.NON_PSEUDO_INERTIAL_FRAME,
                                              name);
                }
            }
        }

        // None of the frames match the name
        throw new IllegalArgumentException("Unknown frame: " + name);

    }

}
