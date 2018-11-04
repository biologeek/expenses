import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountChoiceComponent } from './account-choice.component';

describe('AccountChoiceComponent', () => {
  let component: AccountChoiceComponent;
  let fixture: ComponentFixture<AccountChoiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountChoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountChoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
