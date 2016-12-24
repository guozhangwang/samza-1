/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.samza.coordinator;

import org.apache.samza.job.model.JobModel;

/**
 *  A JobCoordinator is a pluggable module in each process that provides the JobModel and the ID to the StreamProcessor.
 *  In some cases, ID assignment is completely config driven, while in other cases, ID assignment may require
 *  coordination with JobCoordinators of other StreamProcessors.
 *  */
public interface JobCoordinator {
  /**
   * Starts the JobCoordinator which involves one or more of the following:
   * * LeaderElector Module initialization, if any
   * * If leader, generate JobModel. Else, read JobModel
   */
  void start();

  /**
   * Cleanly shutting down the JobCoordinator involves:
   * * Shutting down the LeaderElection module (TBD: details depending on leader or not)
   * * TBD
   */
  void stop();

  /**
   * Returns the logical ID assigned to the processor
   * This may be specified by the user when used as a Library and hence, it is upto the user to ensure that different
   * instances of StreamProcessor have unique processor ID. In all other cases, this will be assigned by the leader?? (Need to think more)
   * @return integer representing the logical processor ID
   */
  int getProcessorId();

  /**
   * Returns the current JobModel
   * The implementation of the JobCoordinator in the leader needs to know how to read the config and generate JobModel
   * In case of a non-leader, the JobCoordinator should simply fetch the jobmodel
   * @return instance of JobModel that describes the partition distribution among the processors (and hence, tasks)
   */
  JobModel getJobModel();
}