import { TestBed } from '@angular/core/testing';

import { ForwardingMessageService } from './forwarding-message.service';

describe('ForwardingMessageService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ForwardingMessageService = TestBed.get(ForwardingMessageService);
    expect(service).toBeTruthy();
  });
});
