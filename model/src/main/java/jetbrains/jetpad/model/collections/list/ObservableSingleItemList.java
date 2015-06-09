/*
 * Copyright 2012-2015 JetBrains s.r.o
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
package jetbrains.jetpad.model.collections.list;

public class ObservableSingleItemList<ItemT> extends AbstractObservableList<ItemT> {
  private ItemT myItem;
  private boolean myEmpty = true;

  public ObservableSingleItemList() {
  }

  public ObservableSingleItemList(ItemT item) {
    doSet(item);
  }

  public ItemT getItem() {
    return get(0);
  }

  public void setItem(ItemT item) {
    set(0, item);
  }
  
  @Override
  public ItemT get(int index) {
    if (myEmpty || index != 0) {
      throw new IndexOutOfBoundsException();
    }
    return myItem;
  }

  @Override
  public int size() {
    return myEmpty ? 0 : 1;
  }

  @Override
  public ItemT set(int index, ItemT t) {
    ItemT oldValue = myEmpty ? null : remove(index);
    add(index, t);
    return oldValue;
  }

  @Override
  protected void checkAdd(int index, ItemT item) {
    super.checkAdd(index, item);
    if (!myEmpty) {
      throw new IllegalStateException();
    }
  }

  @Override
  protected void doAdd(int index, ItemT item) {
    doSet(item);
  }

  @Override
  protected void doRemove(int index) {
    myItem = null;
    myEmpty = true;
  }

  private void doSet(ItemT item) {
    myItem = item;
    myEmpty = false;
  }
}