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
 * Original class is named TutorialPropagator.
 */
/**
 * Initial data to initialize an orbit propagator.
 * <p>
 * Data are read from a YAML file.
 * </p>
 * @author Bryan Cazabonne
 */
public class PropagatorConfiguration {

    /** Numerical integrator used by the orbit propagator. */
    private IntegratorConfiguration integrator;

    /** Force model data. */
    private ForceModelConfiguration forceModels;

    /** Flag indicating if the propagator is analytical. */
    private boolean isAnalytical;

    /**
     * Get the numerical integrator used by the orbit propagator.
     * @return the numerical integrator used by the orbit propagator
     */
    public IntegratorConfiguration getIntegrator() {
        return integrator;
    }

    /**
     * Set the numerical integrator used by the orbit propagator.
     * @param integrator numerical integrator used by the orbit propagator
     */
    public void setIntegrator(final IntegratorConfiguration integrator) {
        this.integrator = integrator;
    }

    /**
     * Get the force model data.
     * @return the force model data
     */
    public ForceModelConfiguration getForceModels() {
        return forceModels;
    }

    /**
     * Set the force model data.
     * @param forceModels force model data
     */
    public void setForceModels(final ForceModelConfiguration forceModels) {
        this.forceModels = forceModels;
    }

    /**
     * Get the flag for the propagator type.
     * @return true if the propagator is analytical
     */
    public boolean isAnalytical() {
        return isAnalytical;
    }

    /**
     * Set the flag for the propagator type.
     * @param analytical true if the propagator is analytical
     */
    public void setIsAnalytical(final boolean analytical) {
        this.isAnalytical = analytical;
    }

}
