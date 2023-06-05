/*
 * Copyright 2011-2022 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.avm.vktest.restload;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

public class VkRestLoadTesting_50 extends Simulation {
    HttpProtocolBuilder httpProtocol =
            http
                    .baseUrl("http://localhost:8080")
                    .acceptHeader("*/*")
                    .acceptEncodingHeader("gzip, deflate, br")
                    .userAgentHeader(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0");

    ScenarioBuilder scn =
            scenario("Vk test loading")
                    .exec(
                            http("request_1")
                                    .post("/friend")
                                    .header("content-type", "application/json; charset=utf-8")
                                    .requestTimeout(600)
                                    .body(StringBody("s")))
                    .exec(
                            http("request_2")
                                    .post("/user")
                                    .header("content-type", "application/json; charset=utf-8")
                                    .requestTimeout(600)
                                    .body(StringBody("т")))
                    .exec(
                            http("request_3")
                                    .post("/user")
                                    .header("content-type", "application/json; charset=utf-8")
                                    .requestTimeout(600)
                                    .body(StringBody("s")));

    {
        setUp(scn.injectOpen(constantUsersPerSec(10).during(5)).protocols(httpProtocol));
    }
}
