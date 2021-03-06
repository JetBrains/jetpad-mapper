/*
 * Copyright 2012-2017 JetBrains s.r.o
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
package jetbrains.jetpad.model.property;

import jetbrains.jetpad.base.Registration;
import jetbrains.jetpad.base.function.Function;
import jetbrains.jetpad.model.event.EventHandler;
import jetbrains.jetpad.model.event.EventSource;
import jetbrains.jetpad.model.event.SimpleEventSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class EventSelectionTest {
  private SimpleEventSource<Object> es1 = new SimpleEventSource<>();
  private SimpleEventSource<Object> es2 = new SimpleEventSource<>();
  private Property<Boolean> prop = new ValueProperty<>(false);
  private EventSource<Object> result = Properties.selectEvent(prop, new Function<Boolean, EventSource<Object>>() {
    @Override
    public EventSource<Object> apply(Boolean source) {
      return source ? es1 : es2;
    }
  });
  private EventHandler<Object> handler = Mockito.mock(EventHandler.class);
  private Registration reg;

  @Before
  public void before() {
    reg = result.addHandler(handler);
  }


  @Test
  public void ignoredEvent() {
    es1.fire(null);

    assertFired();
  }


  @Test
  public void event() {
    es2.fire(null);

    assertFired((Object) null);
  }

  @Test
  public void switchEvents() {
    es1.fire("a");
    es2.fire("b");

    prop.set(true);
    es2.fire("c");
    es1.fire("d");

    assertFired("b", "d");
  }

  @Test
  public void unregister() {
    reg.remove();

    assertFired();
  }

  private void assertFired(Object... items) {
    for (Object s : items) {
      Mockito.verify(handler).onEvent(s);
    }
    Mockito.verifyNoMoreInteractions(handler);
  }

}