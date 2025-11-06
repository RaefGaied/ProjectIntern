import {Component, EventEmitter, inject, OnInit, Output} from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import {FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';

import { IUIConfiguration } from '../ui-configuration.model';
import { UIConfigurationService } from '../service/ui-configuration.service';
import { UIConfigurationFormService, UIConfigurationFormGroup } from './ui-configuration-form.service';

@Component({
  standalone: true,
  selector: 'jhi-ui-configuration-update',
  templateUrl: './ui-configuration-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class UIConfigurationUpdateComponent implements OnInit {
  isSaving = false;
  uIConfiguration: IUIConfiguration | null = null;
 @Output() logoUrlChange = new EventEmitter<string>();
 @Output() bannerUrlChange = new EventEmitter<string>();

  protected uIConfigurationService = inject(UIConfigurationService);
  protected uIConfigurationFormService = inject(UIConfigurationFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: UIConfigurationFormGroup = this.uIConfigurationFormService.createUIConfigurationFormGroup();
  // Variables pour stocker les fichiers sélectionnés et les prévisualisations
  selectedLogoFile: File | null = null;
  selectedBannerFile: File | null = null;
  logoPreviewUrl: string | ArrayBuffer | null = null;
  bannerPreviewUrl: string | ArrayBuffer | null = null;

  preloadImage(url: string): void {
    const img = new Image();
    img.src = url;
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uIConfiguration }) => {
      this.uIConfiguration = uIConfiguration;
      this.loadPreviews(uIConfiguration);
      if (uIConfiguration) {
        this.updateForm(uIConfiguration);

        if (uIConfiguration.logo) {
          const logoUrl = `/content/images/${encodeURIComponent(uIConfiguration.logo)}`;
          this.logoPreviewUrl = logoUrl;
          this.preloadImage(logoUrl);  // Preload the logo
        }

        if (uIConfiguration.banner) {
          const bannerUrl = `/content/images/${encodeURIComponent(uIConfiguration.banner)}`;
          this.bannerPreviewUrl = bannerUrl;
          this.preloadImage(bannerUrl);  // Preload the banner
        }
      }
    });
  }

  loadPreviews(uiConfiguration: IUIConfiguration): void {
    if (uiConfiguration.logo) {
      this.logoPreviewUrl = `/content/images/${encodeURIComponent(uiConfiguration.logo)}`;
    }
    if (uiConfiguration.banner) {
      this.bannerPreviewUrl = `/content/images/${encodeURIComponent(uiConfiguration.banner)}`;
    }
  }


  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const uIConfiguration = this.uIConfigurationFormService.getUIConfiguration(this.editForm);
    if (uIConfiguration.id !== null) {
      this.subscribeToSaveResponse(this.uIConfigurationService.update(uIConfiguration));
    } else {
      this.subscribeToSaveResponse(this.uIConfigurationService.create(uIConfiguration));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUIConfiguration>>): void {
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

  protected updateForm(uIConfiguration: IUIConfiguration): void {
    this.uIConfiguration = uIConfiguration;
    this.uIConfigurationFormService.resetForm(this.editForm, uIConfiguration);
  }

  onFileSelected(event: Event, fieldType: string): void {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files[0]) {
      const file = fileInput.files[0];

      // Verify if the file is an image
      const validImageTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/jpg'];
      if (!validImageTypes.includes(file.type)) {
        alert('Veuillez sélectionner une image valide (JPEG, PNG, GIF)');
        return;
      }

      // Check if file size exceeds 1 MB
      const maxSizeInBytes = 1048576; // 1 MB
      if (file.size > maxSizeInBytes) {
        alert('La taille du fichier ne doit pas dépasser 1 Mo.');
        return;
      }

      // Check if the file has changed to prevent multiple processing for the same file
      if (fieldType === 'logo' && this.selectedLogoFile?.name === file.name) {
        return;
      }
      if (fieldType === 'banner' && this.selectedBannerFile?.name === file.name) {
        return;
      }

      const reader = new FileReader();
      reader.onload = (e) => {
        const result = e.target?.result;
        if (fieldType === 'logo') {
          this.selectedLogoFile = file;
          this.logoPreviewUrl = result as string;
          this.editForm.patchValue({ logo: file.name });
          this.logoUrlChange.emit(file.name);  // Emit change event for parent component
        } else if (fieldType === 'banner') {
          this.selectedBannerFile = file;
          this.bannerPreviewUrl = result as string;
          this.editForm.patchValue({ banner: file.name });
          this.bannerUrlChange.emit(file.name);  // Emit change event for parent component
        }
      };
      reader.readAsDataURL(file);
    }
  }










}
