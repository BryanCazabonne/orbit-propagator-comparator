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

/**
 * Class is taken from Orekit Tutorials project developed under Apache V2.0 license.
 * Original class is named TutorialIntegrator.
 */
/**
 * Initial data to initialize a numerical integrator.
 * <p>
 * Data are read from a YAML file.
 * </p>
 * @author Bryan Cazabonne
 */
public class IntegratorConfiguration {

    /** Minimum step size (s). */
    private double minStep;

    /** Maximum step size (s). */
    private double maxStep;

    /** Fixed step size (s). */
    private double fixedStep;

    /** Position error (m). */
    private double positionError;

    /**
     * Get the minimum step size.
     * @return the minimum step size (s)
     */
    public double getMinStep() {
        return minStep;
    }

    /**
     * Set the minimum step size.
     * @param minStep minimum step size (s)
     */
    public void setMinStep(final double minStep) {
        this.minStep = minStep;
    }

    /**
     * Get the maximum step size.
     * @return the maximum step size (s)
     */
    public double getMaxStep() {
        return maxStep;
    }

    /**
     * Set the maximum step size.
     * @param maxStep maximum step size (s)
     */
    public void setMaxStep(final double maxStep) {
        this.maxStep = maxStep;
    }

    /**
     * Get the fixed step size.
     * @return the fixed step size (s)
     */
    public double getFixedStep() {
        return fixedStep;
    }

    /**
     * Set the fixed step size.
     * @param fixedStep fixed step size (s)
     */
    public void setFixedStep(final double fixedStep) {
        this.fixedStep = fixedStep;
    }

    /**
     * Get the position error.
     * @return the position error (m)
     */
    public double getPositionError() {
        return positionError;
    }

    /**
     * Set the position error.
     * @param positionError position error (m)
     */
    public void setPositionError(final double positionError) {
        this.positionError = positionError;
    }

}
