<template>
    <div class="pep-check-container">
      <h3 class="pep-header">PEP Check WIREFRAME</h3>
      
      <v-card class="pa-6">
        <h4 class="text-h5 mb-6">Political Exposed Person Screening</h4>
        
        <!-- Question 1 -->
        <v-card
          outlined
          class="mb-4 pa-4"
        >
          <div class="d-flex justify-space-between align-center flex-wrap">
            <div class="text-body-1 question-text">Are You Affiliated with Any Political Party?</div>
            <div class="checkbox-container">
              <div class="option-group">
                <span class="option-label">Yes</span>
                <v-checkbox
                  v-model="politicalAffiliation"
                  value="yes"
                  hide-details
                  class="option-checkbox"
                  density="compact"
                ></v-checkbox>
              </div>
              
              <div class="option-group">
                <span class="option-label">No</span>
                <v-checkbox
                  v-model="politicalAffiliation"
                  value="no"
                  hide-details
                  class="option-checkbox"
                  density="compact"
                ></v-checkbox>
              </div>
            </div>
          </div>
        </v-card>
        
        <!-- Question 2 -->
        <v-card
          outlined
          class="mb-4 pa-4"
        >
          <div class="d-flex justify-space-between align-center flex-wrap">
            <div class="text-body-1 question-text">Have You ever held any Public office or Related to anyone who Holds office?</div>
            <div class="checkbox-container">
              <div class="option-group">
                <span class="option-label">Yes</span>
                <v-checkbox
                  v-model="publicOffice"
                  value="yes"
                  hide-details
                  class="option-checkbox"
                  density="compact"
                ></v-checkbox>
              </div>
              
              <div class="option-group">
                <span class="option-label">No</span>
                <v-checkbox
                  v-model="publicOffice"
                  value="no"
                  hide-details
                  class="option-checkbox"
                  density="compact"
                ></v-checkbox>
              </div>
            </div>
          </div>
        </v-card>
        
        <!-- Question 3 -->
        <v-card
          outlined
          class="mb-6 pa-4"
        >
          <div class="d-flex justify-space-between align-center flex-wrap">
            <div class="text-body-1 question-text">Have You ever held any Political position or related to anyone who is in a political Party?</div>
            <div class="checkbox-container">
              <div class="option-group">
                <span class="option-label">Yes</span>
                <v-checkbox
                  v-model="politicalPosition"
                  value="yes"
                  hide-details
                  class="option-checkbox"
                  density="compact"
                ></v-checkbox>
              </div>
              
              <div class="option-group">
                <span class="option-label">No</span>
                <v-checkbox
                  v-model="politicalPosition"
                  value="no"
                  hide-details
                  class="option-checkbox"
                  density="compact"
                ></v-checkbox>
              </div>
            </div>
          </div>
        </v-card>
        
        <!-- Submit button -->
        <div class="text-center">
          <v-btn
            color="success"
            size="large"
            :disabled="!isFormValid"
            :loading="isSubmitting"
            @click="submitForm"
            min-width="180"
          >
            Submit
          </v-btn>
        </div>
      </v-card>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue';
  import { useRouter } from 'vue-router';
  
  // Mock router
  const router = useRouter()
  
  // Form state
  const politicalAffiliation = ref('');
  const publicOffice = ref('');
  const politicalPosition = ref('');
  const isSubmitting = ref(false);
  
  // Determine if form is valid and can be submitted
  const isFormValid = computed(() => {
    // All questions must be answered
    return (
      politicalAffiliation.value !== '' &&
      publicOffice.value !== '' &&
      politicalPosition.value !== ''
    );
  });
  
  // Form submission
  const submitForm = async () => {
    if (!isFormValid.value) {
      alert('Please answer all questions');
      return;
    }
    
    isSubmitting.value = true;
    
    try {
      // Prepare form data
      const formData = {
        politicalAffiliation: politicalAffiliation.value,
        publicOffice: publicOffice.value,
        politicalPosition: politicalPosition.value,
        isPEP: politicalAffiliation.value === 'yes' || 
               publicOffice.value === 'yes' || 
               politicalPosition.value === 'yes'
      };
      
      console.log('Submitting PEP data:', formData);
      
      // Simulate API delay
      await new Promise(resolve => setTimeout(resolve, 1500));
      
      // Navigate to next page
      router.replace('/uploadSignature');
    } catch (error) {
      console.error('Error submitting PEP form:', error);
      alert('An error occurred while submitting the form. Please try again.');
    } finally {
      isSubmitting.value = false;
    }
  };
  </script>
  
  <style scoped>
  .pep-check-container {
    max-width: 900px;
    margin: 0 auto;
    padding: 20px;
  }
  
  .pep-header {
    background-color: #e0e0e0;
    padding: 10px;
    margin-bottom: 1px;
    color: #2c3e50;
    font-weight: 500;
  }
  
  /* Better styling for question & answer layout */
  .question-text {
    flex: 1;
    min-width: 300px;
    padding-right: 20px;
  }
  
  .checkbox-container {
    display: flex;
    align-items: center;
    min-width: 150px;
  }
  
  .option-group {
    display: flex;
    align-items: center;
    margin-right: 10px;
  }
  
  .option-label {
    margin-right: 4px;
  }
  
  /* Make checkboxes appear as radio buttons visually */
  :deep(.v-checkbox) .v-selection-control {
    min-height: 24px !important;
    padding: 0 !important;
  }
  
  :deep(.v-selection-control__wrapper) {
    width: 24px !important;
    height: 24px !important;
  }
  
  /* Ensure proper spacing between option groups */
  .option-checkbox {
    margin-right: 12px;
    margin-left: 0;
  }
  
  /* Mobile responsive adjustments */
  @media (max-width: 768px) {
    .checkbox-container {
      margin-top: 10px;
    }
  
    .question-text {
      margin-bottom: 8px;
    }
  }
  </style>