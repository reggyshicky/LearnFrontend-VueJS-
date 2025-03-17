<template>
    <div class="document-upload-container">
      <h1>Upload the Required Documentation</h1>
      
      <v-card class="pa-4">
        <h2 class="text-h5 mb-2">Documents Upload</h2>
        <p class="text-subtitle-1 mb-4">Upload Customer Documents for Verification</p>
        
        <!-- Citizenship Selection -->
        <v-row class="mb-4">
          <v-col cols="12">
            <v-radio-group
              v-model="isForeigner"
              inline
              label="Citizenship Status"
            >
              <v-radio label="Citizen" :value="false"></v-radio>
              <v-radio label="Foreigner" :value="true"></v-radio>
            </v-radio-group>
          </v-col>
        </v-row>
        
        <!-- Document Upload Section -->
        <v-row>
          <!-- First Column - Upload Sections -->
          <v-col cols="12" md="6">
            <v-row>
              <!-- ID Copy Upload -->
              <v-col cols="12">
                <v-card outlined class="pa-3">
                  <div class="text-subtitle-1 mb-2">ID Copy (CISL Stamped)</div>
                  <v-card
                    outlined
                    class="upload-area"
                    :class="{ 'active-drag': isDragging.idCopy }"
                    @dragover.prevent="() => isDragging.idCopy = true"
                    @dragleave.prevent="() => isDragging.idCopy = false"
                    @drop.prevent="(e) => onFileDrop(e, 'idCopy')"
                    @click="() => $refs.idCopyInput.click()"
                  >
                    <input 
                      type="file" 
                      ref="idCopyInput" 
                      @change="(e) => onFileSelected(e, 'idCopy')" 
                      accept=".pdf,.jpg,.jpeg,.png" 
                      class="hidden-input"
                    />
                    
                    <div class="text-center py-6">
                      <v-icon
                        size="40"
                        color="grey-darken-1"
                        class="mb-2"
                      >
                        mdi-cloud-upload-outline
                      </v-icon>
                      
                      <div class="text-body-1 mb-2">
                        Drag & Drop Files Here
                      </div>
                      
                      <v-btn
                        variant="outlined"
                        size="small"
                        class="mb-3"
                        color="black"
                      >
                        Browse Files
                      </v-btn>
                      
                      <div class="text-caption text-grey">
                        Maximum File : 10MB
                      </div>
                    </div>
                  </v-card>
                </v-card>
              </v-col>
              
              <!-- KRA Copy Upload -->
              <v-col cols="12">
                <v-card outlined class="pa-3">
                  <div class="text-subtitle-1 mb-2">KRA Copy (CISL Stamped)</div>
                  <v-card
                    outlined
                    class="upload-area"
                    :class="{ 'active-drag': isDragging.kraCopy }"
                    @dragover.prevent="() => isDragging.kraCopy = true"
                    @dragleave.prevent="() => isDragging.kraCopy = false"
                    @drop.prevent="(e) => onFileDrop(e, 'kraCopy')"
                    @click="() => $refs.kraCopyInput.click()"
                  >
                    <input 
                      type="file" 
                      ref="kraCopyInput" 
                      @change="(e) => onFileSelected(e, 'kraCopy')" 
                      accept=".pdf,.jpg,.jpeg,.png" 
                      class="hidden-input"
                    />
                    
                    <div class="text-center py-6">
                      <v-icon
                        size="40"
                        color="grey-darken-1"
                        class="mb-2"
                      >
                        mdi-cloud-upload-outline
                      </v-icon>
                      
                      <div class="text-body-1 mb-2">
                        Drag & Drop Files Here
                      </div>
                      
                      <v-btn
                        variant="outlined"
                        size="small"
                        class="mb-3"
                        color="black"
                      >
                        Browse Files
                      </v-btn>
                      
                      <div class="text-caption text-grey">
                        Maximum File : 10MB
                      </div>
                    </div>
                  </v-card>
                </v-card>
              </v-col>
              
              <!-- Additional documents for foreigners -->
              <template v-if="isForeigner">
                <!-- Work Permit Upload -->
                <v-col cols="12">
                  <v-card outlined class="pa-3">
                    <div class="text-subtitle-1 mb-2">Work Permit</div>
                    <v-card
                      outlined
                      class="upload-area"
                      :class="{ 'active-drag': isDragging.workPermit }"
                      @dragover.prevent="() => isDragging.workPermit = true"
                      @dragleave.prevent="() => isDragging.workPermit = false"
                      @drop.prevent="(e) => onFileDrop(e, 'workPermit')"
                      @click="() => $refs.workPermitInput.click()"
                    >
                      <input 
                        type="file" 
                        ref="workPermitInput" 
                        @change="(e) => onFileSelected(e, 'workPermit')" 
                        accept=".pdf,.jpg,.jpeg,.png" 
                        class="hidden-input"
                      />
                      
                      <div class="text-center py-6">
                        <v-icon
                          size="40"
                          color="grey-darken-1"
                          class="mb-2"
                        >
                          mdi-cloud-upload-outline
                        </v-icon>
                        
                        <div class="text-body-1 mb-2">
                          Drag & Drop Files Here
                        </div>
                        
                        <v-btn
                          variant="outlined"
                          size="small"
                          class="mb-3"
                          color="black"
                        >
                          Browse Files
                        </v-btn>
                        
                        <div class="text-caption text-grey">
                          Maximum File : 10MB
                        </div>
                      </div>
                    </v-card>
                  </v-card>
                </v-col>
                
                <!-- Passport Upload -->
                <v-col cols="12">
                  <v-card outlined class="pa-3">
                    <div class="text-subtitle-1 mb-2">Valid Passport</div>
                    <v-card
                      outlined
                      class="upload-area"
                      :class="{ 'active-drag': isDragging.passport }"
                      @dragover.prevent="() => isDragging.passport = true"
                      @dragleave.prevent="() => isDragging.passport = false"
                      @drop.prevent="(e) => onFileDrop(e, 'passport')"
                      @click="() => $refs.passportInput.click()"
                    >
                      <input 
                        type="file" 
                        ref="passportInput" 
                        @change="(e) => onFileSelected(e, 'passport')" 
                        accept=".pdf,.jpg,.jpeg,.png" 
                        class="hidden-input"
                      />
                      
                      <div class="text-center py-6">
                        <v-icon
                          size="40"
                          color="grey-darken-1"
                          class="mb-2"
                        >
                          mdi-cloud-upload-outline
                        </v-icon>
                        
                        <div class="text-body-1 mb-2">
                          Drag & Drop Files Here
                        </div>
                        
                        <v-btn
                          variant="outlined"
                          size="small"
                          class="mb-3"
                          color="black"
                        >
                          Browse Files
                        </v-btn>
                        
                        <div class="text-caption text-grey">
                          Maximum File : 10MB
                        </div>
                      </div>
                    </v-card>
                  </v-card>
                </v-col>
                
                <!-- FATCA Form -->
                <v-col cols="12">
                  <v-card outlined class="pa-3">
                    <div class="text-subtitle-1 mb-2">FATCA Details</div>
                    <v-textarea
                      v-model="fatcaDetails"
                      outlined
                      rows="3"
                      placeholder="Enter FATCA Details"
                    ></v-textarea>
                  </v-card>
                </v-col>
              </template>
            </v-row>
          </v-col>
          
          <!-- Second Column - Document Previews -->
          <v-col cols="12" md="6">
            <v-row>
              <!-- ID Copy Preview -->
              <v-col cols="12">
                <v-card outlined class="pa-3 preview-card">
                  <div class="d-flex justify-space-between align-center mb-2">
                    <div class="text-subtitle-1">Document Preview</div>
                    <div v-if="uploadedFiles.idCopy" class="d-flex align-center">
                      <span class="text-body-2 mr-2">ID Copy</span>
                      <v-btn
                        icon="mdi-close"
                        size="small"
                        variant="text"
                        @click="removeFile('idCopy')"
                      ></v-btn>
                    </div>
                  </div>
                  
                  <div class="document-preview-container">
                    <div v-if="uploadedFiles.idCopy" class="document-preview">
                      <div v-if="isImageFile(uploadedFiles.idCopy)">
                        <img :src="filePreviewUrls.idCopy" alt="ID Copy Preview" class="preview-image" />
                      </div>
                      <div v-else class="pdf-preview d-flex flex-column align-center justify-center">
                        <v-icon size="64" color="grey-darken-1">mdi-file-pdf-box</v-icon>
                        <span class="text-caption mt-2">{{ uploadedFiles.idCopy.name }}</span>
                      </div>
                    </div>
                    <div v-else class="empty-preview d-flex flex-column align-center justify-center">
                      <v-icon size="64" color="grey-lighten-1">mdi-file-document-outline</v-icon>
                      <span class="text-caption text-grey mt-2">No document uploaded</span>
                    </div>
                  </div>
                </v-card>
              </v-col>
              
              <!-- KRA Copy Preview -->
              <v-col cols="12">
                <v-card outlined class="pa-3 preview-card">
                  <div class="d-flex justify-space-between align-center mb-2">
                    <div class="text-subtitle-1">Document Preview</div>
                    <div v-if="uploadedFiles.kraCopy" class="d-flex align-center">
                      <span class="text-body-2 mr-2">KRA COPY</span>
                      <v-btn
                        icon="mdi-close"
                        size="small"
                        variant="text"
                        @click="removeFile('kraCopy')"
                      ></v-btn>
                    </div>
                  </div>
                  
                  <div class="document-preview-container">
                    <div v-if="uploadedFiles.kraCopy" class="document-preview">
                      <div v-if="isImageFile(uploadedFiles.kraCopy)">
                        <img :src="filePreviewUrls.kraCopy" alt="KRA Copy Preview" class="preview-image" />
                      </div>
                      <div v-else class="pdf-preview d-flex flex-column align-center justify-center">
                        <v-icon size="64" color="grey-darken-1">mdi-file-pdf-box</v-icon>
                        <span class="text-caption mt-2">{{ uploadedFiles.kraCopy.name }}</span>
                      </div>
                    </div>
                    <div v-else class="empty-preview d-flex flex-column align-center justify-center">
                      <v-icon size="64" color="grey-lighten-1">mdi-file-document-outline</v-icon>
                      <span class="text-caption text-grey mt-2">No document uploaded</span>
                    </div>
                  </div>
                </v-card>
              </v-col>
              
              <!-- Additional document previews for foreigners -->
              <template v-if="isForeigner">
                <!-- Work Permit Preview -->
                <v-col cols="12">
                  <v-card outlined class="pa-3 preview-card">
                    <div class="d-flex justify-space-between align-center mb-2">
                      <div class="text-subtitle-1">Document Preview</div>
                      <div v-if="uploadedFiles.workPermit" class="d-flex align-center">
                        <span class="text-body-2 mr-2">Work Permit</span>
                        <v-btn
                          icon="mdi-close"
                          size="small"
                          variant="text"
                          @click="removeFile('workPermit')"
                        ></v-btn>
                      </div>
                    </div>
                    
                    <div class="document-preview-container">
                      <div v-if="uploadedFiles.workPermit" class="document-preview">
                        <div v-if="isImageFile(uploadedFiles.workPermit)">
                          <img :src="filePreviewUrls.workPermit" alt="Work Permit Preview" class="preview-image" />
                        </div>
                        <div v-else class="pdf-preview d-flex flex-column align-center justify-center">
                          <v-icon size="64" color="grey-darken-1">mdi-file-pdf-box</v-icon>
                          <span class="text-caption mt-2">{{ uploadedFiles.workPermit.name }}</span>
                        </div>
                      </div>
                      <div v-else class="empty-preview d-flex flex-column align-center justify-center">
                        <v-icon size="64" color="grey-lighten-1">mdi-file-document-outline</v-icon>
                        <span class="text-caption text-grey mt-2">No document uploaded</span>
                      </div>
                    </div>
                  </v-card>
                </v-col>
                
                <!-- Passport Preview -->
                <v-col cols="12">
                  <v-card outlined class="pa-3 preview-card">
                    <div class="d-flex justify-space-between align-center mb-2">
                      <div class="text-subtitle-1">Document Preview</div>
                      <div v-if="uploadedFiles.passport" class="d-flex align-center">
                        <span class="text-body-2 mr-2">Passport</span>
                        <v-btn
                          icon="mdi-close"
                          size="small"
                          variant="text"
                          @click="removeFile('passport')"
                        ></v-btn>
                      </div>
                    </div>
                    
                    <div class="document-preview-container">
                      <div v-if="uploadedFiles.passport" class="document-preview">
                        <div v-if="isImageFile(uploadedFiles.passport)">
                          <img :src="filePreviewUrls.passport" alt="Passport Preview" class="preview-image" />
                        </div>
                        <div v-else class="pdf-preview d-flex flex-column align-center justify-center">
                          <v-icon size="64" color="grey-darken-1">mdi-file-pdf-box</v-icon>
                          <span class="text-caption mt-2">{{ uploadedFiles.passport.name }}</span>
                        </div>
                      </div>
                      <div v-else class="empty-preview d-flex flex-column align-center justify-center">
                        <v-icon size="64" color="grey-lighten-1">mdi-file-document-outline</v-icon>
                        <span class="text-caption text-grey mt-2">No document uploaded</span>
                      </div>
                    </div>
                  </v-card>
                </v-col>
              </template>
            </v-row>
          </v-col>
        </v-row>
        
        <!-- Submit Button -->
        <v-row class="mt-6">
          <v-col cols="12" class="d-flex justify-end">
            <v-btn
              color="black"
              size="large"
              :disabled="!canSubmit"
              :loading="isSubmitting"
              @click="uploadDocuments"
            >
              Upload Documents
            </v-btn>
          </v-col>
        </v-row>
      </v-card>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, reactive } from 'vue';
  import { useRouter } from 'vue-router';
  const router = useRouter()
  
  // State variables
  const isForeigner = ref(false);
  const isSubmitting = ref(false);
  const fatcaDetails = ref('');
  
  // File states
  const uploadedFiles = reactive({
    idCopy: null,
    kraCopy: null,
    workPermit: null,
    passport: null
  });
  
  const filePreviewUrls = reactive({
    idCopy: null,
    kraCopy: null,
    workPermit: null,
    passport: null
  });
  
  const isDragging = reactive({
    idCopy: false,
    kraCopy: false,
    workPermit: false,
    passport: false
  });
  
  // Validation
  const canSubmit = computed(() => {
    if (isForeigner.value) {
      return (
        uploadedFiles.idCopy && 
        uploadedFiles.kraCopy && 
        uploadedFiles.workPermit && 
        uploadedFiles.passport &&
        fatcaDetails.value.trim() !== ''
      );
    } else {
      return uploadedFiles.idCopy && uploadedFiles.kraCopy;
    }
  });
  
  // File handling functions
  const onFileSelected = (event, fileType) => {
    const file = event.target.files[0];
    if (file) {
      if (file.size > 10 * 1024 * 1024) { // 10MB limit
        alert('File size exceeds the limit of 10MB');
        return;
      }
      
      uploadedFiles[fileType] = file;
      createFilePreview(file, fileType);
    }
  };
  
  const onFileDrop = (event, fileType) => {
    isDragging[fileType] = false;
    const file = event.dataTransfer.files[0];
    
    if (file) {
      if (file.size > 10 * 1024 * 1024) { // 10MB limit
        alert('File size exceeds the limit of 10MB');
        return;
      }
      
      uploadedFiles[fileType] = file;
      createFilePreview(file, fileType);
    }
  };
  
  const removeFile = (fileType) => {
    uploadedFiles[fileType] = null;
    filePreviewUrls[fileType] = null;
    
    // Reset the file input
    if (fileType === 'idCopy') this.$refs.idCopyInput.value = '';
    else if (fileType === 'kraCopy') this.$refs.kraCopyInput.value = '';
    else if (fileType === 'workPermit') this.$refs.workPermitInput.value = '';
    else if (fileType === 'passport') this.$refs.passportInput.value = '';
  };
  
  const createFilePreview = (file, fileType) => {
    if (isImageFile(file)) {
      const reader = new FileReader();
      reader.onload = (e) => {
        filePreviewUrls[fileType] = e.target.result;
      };
      reader.readAsDataURL(file);
    } else {
      // For PDFs, we don't generate a preview, we just indicate it's a PDF
      filePreviewUrls[fileType] = null;
    }
  };
  
  const isImageFile = (file) => {
    if (!file) return false;
    return file.type.startsWith('image/');
  };
  
  // Upload documents
  const uploadDocuments = async () => {
    if (!canSubmit.value) {
      alert('Please upload all required documents');
      return;
    }
    
    isSubmitting.value = true;
    
    try {
      // Create form data
      const formData = new FormData();
      formData.append('isForeigner', isForeigner.value);
      formData.append('idCopy', uploadedFiles.idCopy);
      formData.append('kraCopy', uploadedFiles.kraCopy);
      
      if (isForeigner.value) {
        formData.append('workPermit', uploadedFiles.workPermit);
        formData.append('passport', uploadedFiles.passport);
        formData.append('fatcaDetails', fatcaDetails.value);
      }
      
      // For debugging - log form data
      console.log('Form data entries:');
      for (const [key, value] of formData.entries()) {
        console.log(`${key}: ${value instanceof File ? value.name : value}`);
      }
      
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 2000));
      
      // Navigate to next page after successful upload
      router.replace('/pepCheck');
    } catch (error) {
      console.error('Error uploading documents:', error);
      alert('An error occurred while uploading documents. Please try again.');
    } finally {
      isSubmitting.value = false;
    }
  };
  </script>
  
  <style scoped>
  .document-upload-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
  }
  
  h1 {
    color: #2c3e50;
    margin-bottom: 20px;
    border-bottom: 2px solid #eee;
    padding-bottom: 10px;
  }
  
  .upload-area {
    cursor: pointer;
    transition: background-color 0.3s, border-color 0.3s;
    height: 100%;
  }
  
  .upload-area:hover {
    background-color: #f5f5f5;
  }
  
  .active-drag {
    background-color: #f0f7ff;
    border-color: #2196F3 !important;
  }
  
  .hidden-input {
    display: none;
  }
  
  .document-preview-container {
    height: 200px;
    background-color: #f1f1f1;
    border-radius: 4px;
    overflow: hidden;
  }
  
  .preview-image {
    width: 100%;
    height: 100%;
    object-fit: contain;
  }
  
  .empty-preview, .pdf-preview {
    height: 100%;
    background-color: #f1f1f1;
  }
  
  .preview-card {
    height: 100%;
  }
  </style>