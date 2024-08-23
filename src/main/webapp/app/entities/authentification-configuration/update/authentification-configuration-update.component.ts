import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAuthentificationConfiguration } from '../authentification-configuration.model';
import { AuthentificationConfigurationService } from '../service/authentification-configuration.service';
import {
  AuthentificationConfigurationFormService,
  AuthentificationConfigurationFormGroup,
} from './authentification-configuration-form.service';

@Component({
  standalone: true,
  selector: 'jhi-authentification-configuration-update',
  templateUrl: './authentification-configuration-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AuthentificationConfigurationUpdateComponent implements OnInit {
  isSaving = false;
  authentificationConfiguration: IAuthentificationConfiguration | null = null;

  protected authentificationConfigurationService = inject(AuthentificationConfigurationService);
  protected authentificationConfigurationFormService = inject(AuthentificationConfigurationFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AuthentificationConfigurationFormGroup =
    this.authentificationConfigurationFormService.createAuthentificationConfigurationFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ authentificationConfiguration }) => {
      this.authentificationConfiguration = authentificationConfiguration;
      if (authentificationConfiguration) {
        this.updateForm(authentificationConfiguration);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const authentificationConfiguration = this.authentificationConfigurationFormService.getAuthentificationConfiguration(this.editForm);
    if (authentificationConfiguration.id !== null) {
      this.subscribeToSaveResponse(this.authentificationConfigurationService.update(authentificationConfiguration));
    } else {
      this.subscribeToSaveResponse(this.authentificationConfigurationService.create(authentificationConfiguration));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuthentificationConfiguration>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(authentificationConfiguration: IAuthentificationConfiguration): void {
    this.authentificationConfiguration = authentificationConfiguration;
    this.authentificationConfigurationFormService.resetForm(this.editForm, authentificationConfiguration);
  }
}
