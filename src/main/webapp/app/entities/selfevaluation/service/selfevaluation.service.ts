import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISelfevaluation, getSelfevaluationIdentifier } from '../selfevaluation.model';

export type EntityResponseType = HttpResponse<ISelfevaluation>;
export type EntityArrayResponseType = HttpResponse<ISelfevaluation[]>;

@Injectable({ providedIn: 'root' })
export class SelfevaluationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/selfevaluations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(selfevaluation: ISelfevaluation): Observable<EntityResponseType> {
    return this.http.post<ISelfevaluation>(this.resourceUrl, selfevaluation, { observe: 'response' });
  }

  update(selfevaluation: ISelfevaluation): Observable<EntityResponseType> {
    return this.http.put<ISelfevaluation>(`${this.resourceUrl}/${getSelfevaluationIdentifier(selfevaluation) as number}`, selfevaluation, {
      observe: 'response',
    });
  }

  partialUpdate(selfevaluation: ISelfevaluation): Observable<EntityResponseType> {
    return this.http.patch<ISelfevaluation>(
      `${this.resourceUrl}/${getSelfevaluationIdentifier(selfevaluation) as number}`,
      selfevaluation,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISelfevaluation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISelfevaluation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  sktquery(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISelfevaluation[]>(this.resourceUrl + '/currentuser', { params: options, observe: 'response' });
  }

  findByTeamAndSkill(teamId: number, skillId: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISelfevaluation[]>(`${this.resourceUrl}/teamAndSkill/${teamId}/${skillId}`, {
      params: options,
      observe: 'response',
    });
  }

  findBySkillDesc(skillId: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISelfevaluation[]>(`${this.resourceUrl}/expert/${skillId}`, {
      params: options,
      observe: 'response',
    });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSelfevaluationToCollectionIfMissing(
    selfevaluationCollection: ISelfevaluation[],
    ...selfevaluationsToCheck: (ISelfevaluation | null | undefined)[]
  ): ISelfevaluation[] {
    const selfevaluations: ISelfevaluation[] = selfevaluationsToCheck.filter(isPresent);
    if (selfevaluations.length > 0) {
      const selfevaluationCollectionIdentifiers = selfevaluationCollection.map(
        selfevaluationItem => getSelfevaluationIdentifier(selfevaluationItem)!
      );
      const selfevaluationsToAdd = selfevaluations.filter(selfevaluationItem => {
        const selfevaluationIdentifier = getSelfevaluationIdentifier(selfevaluationItem);
        if (selfevaluationIdentifier == null || selfevaluationCollectionIdentifiers.includes(selfevaluationIdentifier)) {
          return false;
        }
        selfevaluationCollectionIdentifiers.push(selfevaluationIdentifier);
        return true;
      });
      return [...selfevaluationsToAdd, ...selfevaluationCollection];
    }
    return selfevaluationCollection;
  }
}
