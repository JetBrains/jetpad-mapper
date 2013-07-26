/*
 * Copyright 2012-2013 JetBrains s.r.o
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jetbrains.jetpad.json.adHoc;

import jetbrains.jetpad.json.JsonString;

class JsonStringSerializer extends Serializer<JsonString> {
  private Serializer<String> myStringSerializer;

  JsonStringSerializer(Serializer<String> stringSerializer) {
    super(4);
    myStringSerializer = stringSerializer;
  }

  @Override
  byte[] write(JsonString data) {
    byte[] value = myStringSerializer.write(data.getStringValue());
    byte[] result = new byte[value.length + 1];
    result[0] = getId();
    System.arraycopy(value, 0, result, 1, value.length);
    return result;
  }

  @Override
  JsonString read(byte[] input, int position) {
    String s = myStringSerializer.read(input, position);
    setPosition(myStringSerializer.getPosition());
    return new JsonString(s);
  }
}