/**
 * Copyright (C) 2015 Red Hat, Inc.                                        
 *                                                                         
 * Licensed under the Apache License, Version 2.0 (the "License");         
 * you may not use this file except in compliance with the License.        
 * You may obtain a copy of the License at                                 
 *                                                                         
 *         http://www.apache.org/licenses/LICENSE-2.0                      
 *                                                                         
 * Unless required by applicable law or agreed to in writing, software     
 * distributed under the License is distributed on an "AS IS" BASIS,       
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and     
 * limitations under the License.
 */

package io.fabric8.openshift.client.mock.impl.doneables;

import io.fabric8.kubernetes.api.builder.Visitor;
import io.fabric8.kubernetes.api.model.Doneable;
import io.fabric8.kubernetes.client.mock.MockDoneable;
import io.fabric8.openshift.api.model.DoneableGroup;
import io.fabric8.openshift.api.model.Group;
import io.fabric8.openshift.api.model.GroupBuilder;
import io.fabric8.openshift.api.model.GroupFluent;
import io.fabric8.openshift.api.model.GroupFluentImpl;
import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;

public class MockDoneableGroup extends GroupFluentImpl<MockDoneableGroup> implements MockDoneable<Group> {

  private interface DelegateInterface extends Doneable<Group>, GroupFluent<DoneableGroup> {}
  private final Visitor<Group> visitor = new Visitor<Group>() {
    @Override
    public void visit(Group item) {
    }
  };

  private final DelegateInterface delegate;

  public MockDoneableGroup() {
    super(new GroupBuilder()
      .withNewMetadata().endMetadata()
      .build());
    this.delegate = EasyMock.createMock(DelegateInterface.class);
  }

  @Override
  public IExpectationSetters<Group> done() {
    return EasyMock.expect(delegate.done());
  }

  @Override
  public Void replay() {
    EasyMock.replay(delegate);
    return null;
  }

  @Override
  public void verify() {
    EasyMock.verify(delegate);
  }

  @Override
  public Doneable<Group> getDelegate() {
    return new DoneableGroup(new GroupBuilder(this).build(), visitor) {
      @Override
      public Group done() {
        return delegate.done();
      }
    };
  }
}