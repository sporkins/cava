/*
 * Copyright 2018 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package net.consensys.cava.crypto.sodium;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AuthTest {

  @BeforeAll
  static void checkAvailable() {
    assumeTrue(Sodium.isAvailable(), "Sodium native library is not available");
  }

  @Test
  void checkAuthenticateAndVerify() {
    Auth.Key key = Auth.Key.random();

    byte[] input = "An input to authenticate".getBytes(UTF_8);
    byte[] tag = Auth.auth(input, key);

    assertTrue(Auth.verify(tag, input, key));
    assertFalse(Auth.verify(new byte[tag.length], input, key));
    assertFalse(Auth.verify(tag, "An invalid input".getBytes(UTF_8), key));
    assertFalse(Auth.verify(tag, input, Auth.Key.random()));
  }
}
