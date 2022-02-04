import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISelfevaluation, Selfevaluation } from '../selfevaluation.model';

import { SelfevaluationService } from './selfevaluation.service';

describe('Selfevaluation Service', () => {
  let service: SelfevaluationService;
  let httpMock: HttpTestingController;
  let elemDefault: ISelfevaluation;
  let expectedResult: ISelfevaluation | ISelfevaluation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SelfevaluationService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      value: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Selfevaluation', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Selfevaluation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Selfevaluation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          value: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Selfevaluation', () => {
      const patchObject = Object.assign(
        {
          value: 1,
        },
        new Selfevaluation()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Selfevaluation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          value: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Selfevaluation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSelfevaluationToCollectionIfMissing', () => {
      it('should add a Selfevaluation to an empty array', () => {
        const selfevaluation: ISelfevaluation = { id: 123 };
        expectedResult = service.addSelfevaluationToCollectionIfMissing([], selfevaluation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(selfevaluation);
      });

      it('should not add a Selfevaluation to an array that contains it', () => {
        const selfevaluation: ISelfevaluation = { id: 123 };
        const selfevaluationCollection: ISelfevaluation[] = [
          {
            ...selfevaluation,
          },
          { id: 456 },
        ];
        expectedResult = service.addSelfevaluationToCollectionIfMissing(selfevaluationCollection, selfevaluation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Selfevaluation to an array that doesn't contain it", () => {
        const selfevaluation: ISelfevaluation = { id: 123 };
        const selfevaluationCollection: ISelfevaluation[] = [{ id: 456 }];
        expectedResult = service.addSelfevaluationToCollectionIfMissing(selfevaluationCollection, selfevaluation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(selfevaluation);
      });

      it('should add only unique Selfevaluation to an array', () => {
        const selfevaluationArray: ISelfevaluation[] = [{ id: 123 }, { id: 456 }, { id: 98606 }];
        const selfevaluationCollection: ISelfevaluation[] = [{ id: 123 }];
        expectedResult = service.addSelfevaluationToCollectionIfMissing(selfevaluationCollection, ...selfevaluationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const selfevaluation: ISelfevaluation = { id: 123 };
        const selfevaluation2: ISelfevaluation = { id: 456 };
        expectedResult = service.addSelfevaluationToCollectionIfMissing([], selfevaluation, selfevaluation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(selfevaluation);
        expect(expectedResult).toContain(selfevaluation2);
      });

      it('should accept null and undefined values', () => {
        const selfevaluation: ISelfevaluation = { id: 123 };
        expectedResult = service.addSelfevaluationToCollectionIfMissing([], null, selfevaluation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(selfevaluation);
      });

      it('should return initial array if no Selfevaluation is added', () => {
        const selfevaluationCollection: ISelfevaluation[] = [{ id: 123 }];
        expectedResult = service.addSelfevaluationToCollectionIfMissing(selfevaluationCollection, undefined, null);
        expect(expectedResult).toEqual(selfevaluationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
