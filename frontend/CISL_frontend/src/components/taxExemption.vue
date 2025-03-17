<template>
  <div class="tax-exemption-container">
    <h1>Tax Exemption Certificate</h1>

    <v-card class="pa-4">
      <v-card-title class="text-center">Tax Exemption</v-card-title>

      <v-row class="mb-4">
        <v-col cols="12">
          <div class="form-question">Are you exempted from tax</div>
          <v-radio-group v-model="isExempted" inline>
            <v-radio label="Yes" value="Yes"></v-radio>
            <v-radio label="No" value="No"></v-radio>
          </v-radio-group>
        </v-col>
      </v-row>

      <v-expand-transition>
        <div v-if="isExempted === 'Yes'">
          <v-alert
            type="info"
            variant="tonal"
            density="compact"
            class="mb-4"
          >
            If Exempted, Fill Below details
          </v-alert>

          <div class="certificate-details">
            <h3>Certificate Details</h3>

            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="certificateNumber"
                  label="Certificate Number"
                  outlined
                  dense
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" sm="6">
                <v-menu
                  v-model="issueMenu"
                  :close-on-content-click="false"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ props }">
                    <v-text-field
                      v-model="formattedIssueDate"
                      label="Issue Date"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="props"
                      outlined
                      dense
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="rawIssueDate"
                    @update:model-value="handleIssueDateChange"
                  ></v-date-picker>
                </v-menu>
              </v-col>

              <v-col cols="12" sm="6">
                <v-menu
                  v-model="expiryMenu"
                  :close-on-content-click="false"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ props }">
                    <v-text-field
                      v-model="formattedExpiryDate"
                      label="Expiry Date"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="props"
                      outlined
                      dense
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="rawExpiryDate"
                    @update:model-value="handleExpiryDateChange"
                  ></v-date-picker>
                </v-menu>
              </v-col>
            </v-row>


            <v-row>
              <v-col cols="12">
                <label class="text-subtitle-1 mb-2 d-block">Document Upload</label>
                <v-card
                  class="upload-area"
                  outlined
                  :class="{ 'active-drag': isDragging }"
                  @dragover.prevent="isDragging = true"
                  @dragleave.prevent="isDragging = false"
                  @drop.prevent="onFileDrop"
                  @click="$refs.fileInput.click()"
                >
                  <input
                    type="file"
                    ref="fileInput"
                    @change="onFileSelected"
                    accept=".pdf,.jpg,.jpeg,.png"
                    class="hidden-input"
                  />

                  <v-card-text class="text-center py-6">
                    <v-icon
                      size="64"
                      color="grey darken-1"
                      class="mb-4"
                    >
                      mdi-cloud-upload-outline
                    </v-icon>

                    <div class="text-body-1 mb-2">
                      Drag and drop your certificate here
                    </div>

                    <v-btn
                      variant="outlined"
                      size="small"
                      color="primary"
                      class="mb-4"
                      @click.stop="$refs.fileInput.click()"
                    >
                      Browse Files
                    </v-btn>

                    <div class="text-caption text-grey">
                      Supported formats: PDF, JPG/JPEG, PNG. Max size: 5MB
                    </div>
                  </v-card-text>
                </v-card>

                <v-slide-y-transition>
                  <v-sheet
                    v-if="selectedFile"
                    class="selected-file mt-2 pa-2"
                    rounded
                  >
                    <div class="d-flex align-center justify-space-between">
                      <div class="d-flex align-center">
                        <v-icon class="mr-2">mdi-file-document-outline</v-icon>
                        <div>
                          <span class="text-subtitle-2">{{ selectedFile.name }}</span>
                          <span class="text-caption ml-2 text-grey">({{ formatFileSize(selectedFile.size) }})</span>
                        </div>
                      </div>
                      <v-btn
                        icon="mdi-close"
                        size="small"
                        variant="text"
                        color="error"
                        @click.stop="removeFile"
                      ></v-btn>
                    </div>
                  </v-sheet>
                </v-slide-y-transition>
              </v-col>
            </v-row>
          </div>
        </div>
      </v-expand-transition>

      <v-row class="mt-6">
        <v-col cols="12" class="d-flex justify-end">
          <v-btn
            color="primary"
            :disabled="!canSubmit"
            @click="submitCertificate"
            :loading="isSubmitting"
          >
            SUBMIT CERTIFICATE
          </v-btn>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter()

// Form state
const isExempted = ref('');
const certificateNumber = ref('');
const selectedFile = ref(null);
const isDragging = ref(false);
const isSubmitting = ref(false);

const issueMenu = ref(false)
const expiryMenu = ref(false)

const rawIssueDate = ref(null)
const rawExpiryDate = ref(null)

const formattedIssueDate = ref('')
const formattedExpiryDate = ref('')

// Handle Issue Date Change
const handleIssueDateChange = (date) => {
  rawIssueDate.value = date
  formattedIssueDate.value = formatDate(date)
  issueMenu.value = false
}

// Handle Expiry Date Change
const handleExpiryDateChange = (date) => {
  rawExpiryDate.value = date
  formattedExpiryDate.value = formatDate(date)
  expiryMenu.value = false
}

// Date Formatter to dd-mm-yyyy
const formatDate = (date) => {
  if (!date) return ''
  const d = new Date(date)
  const day = String(d.getDate()).padStart(2, '0')
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const year = d.getFullYear()
  return `${day}-${month}-${year}`
}

// Form validation
const canSubmit = computed(() => {
  if (isExempted.value === 'No') return true;

  return (
    isExempted.value === 'Yes' &&
    certificateNumber.value &&
    formattedIssueDate.value &&
    formattedExpiryDate.value &&
    selectedFile.value !== null
  );
});

// Format date from ISO (YYYY-MM-DD) to dd-mm-yyyy
const formatDateFromISO = (isoDate) => {
  if (!isoDate) return '';
  const parts = isoDate.split('-');
  return `${parts[2]}-${parts[1]}-${parts[0]}`;
};

// File handlers
const onFileSelected = (event) => {
  const file = event.target.files[0];
  if (file) {
    if (file.size > 5 * 1024 * 1024) {
      alert('File size exceeds the limit of 5MB');
      return;
    }
    selectedFile.value = file;
  }
};

const onFileDrop = (event) => {
  isDragging.value = false;
  const file = event.dataTransfer.files[0];
  if (file) {
    if (file.size > 5 * 1024 * 1024) {
      alert('File size exceeds the limit of 5MB');
      return;
    }
    selectedFile.value = file;
  }
};

const removeFile = () => {
  selectedFile.value = null;
  if (this.$refs && this.$refs.fileInput) {
    this.$refs.fileInput.value = '';
  }
};

const formatFileSize = (bytes) => {
  if (bytes === 0) return '0 Bytes';
  const k = 1024;
  const sizes = ['Bytes', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
};

// Submit form
const submitCertificate = async () => {
  if (!canSubmit.value) {
    alert('Please fill in all required fields');
    return;
  }

  isSubmitting.value = true;

  // Prepare form data for submission
  const formData = new FormData();
  formData.append('isExempted', isExempted.value);

  if (isExempted.value === 'Yes') {
    formData.append('certificateNumber', certificateNumber.value);
    formData.append('formattedIssueDate', formattedIssueDate.value);
    formData.append('formattedExpiryDate', formattedExpiryDate.value);
    formData.append('document', selectedFile.value);
  }

  try {
    // For debugging
    console.log('Form data being submitted:');
    for (const [key, value] of formData.entries()) {
      console.log(`${key}: ${value instanceof File ? value.name : value}`);
    }

    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1500));

    // Navigate to next page
    router.replace('/investmentPlan');
  } catch (error) {
    console.error('Error submitting form:', error);
    alert('An error occurred while submitting the form. Please try again.');
  } finally {
    isSubmitting.value = false;
  }
};
</script>

<style scoped>
.tax-exemption-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  color: #2c3e50;
  margin-bottom: 20px;
  border-bottom: 2px solid #eee;
  padding-bottom: 10px;
}

.form-question {
  font-weight: bold;
  margin-bottom: 10px;
}

.hidden-input {
  display: none;
}

.upload-area {
  border: 2px dashed #ccc;
  cursor: pointer;
  transition: background-color 0.3s, border-color 0.3s;
}

.upload-area:hover {
  background-color: #f5f5f5;
  border-color: #999;
}

.active-drag {
  background-color: #f0f7ff;
  border-color: #2196F3;
}

.selected-file {
  background-color: #f5f5f5;
}
</style>
