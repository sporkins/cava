/*
 * Copyright 2019 ConsenSys AG.
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
package net.consensys.cava.crypto.mikuli;

import static org.apache.milagro.amcl.BLS381.BIG.MODBYTES;

import net.consensys.cava.bytes.Bytes;

import java.util.Objects;

import org.apache.milagro.amcl.BLS381.BIG;

/**
 * This class represents a BLS12-381 private key.
 */
public final class SecretKey {

  /**
   * Create a private key from a byte array
   *
   * @param bytes the bytes of the private key
   * @return a new SecretKey object
   */
  public static SecretKey fromBytes(byte[] bytes) {
    return fromBytes(Bytes.wrap(bytes));
  }

  /**
   * Create a private key from bytes
   *
   * @param bytes the bytes of the private key
   * @return a new SecretKey object
   */
  public static SecretKey fromBytes(Bytes bytes) {
    return new SecretKey(new Scalar(BIG.fromBytes(bytes.toArrayUnsafe())));
  }

  private final Scalar scalarValue;

  SecretKey(Scalar value) {
    this.scalarValue = value;
  }

  G2Point sign(G2Point message) {
    return message.mul(scalarValue);
  }

  public Bytes toBytes() {
    byte[] bytea = new byte[MODBYTES];
    scalarValue.value().toBytes(bytea);
    return Bytes.wrap(bytea);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    SecretKey secretKey = (SecretKey) o;
    return Objects.equals(scalarValue, secretKey.scalarValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scalarValue);
  }
}
