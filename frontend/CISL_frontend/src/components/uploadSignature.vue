<template>
    <div class="signature-container">
      <h3 class="text-center text-h4 mb-6">Upload Your Signature</h3>
      
      <!-- Upload Signature Section -->
      <v-card outlined class="mb-8 pa-4 upload-area" 
        :class="{ 'active-drag': isDragging }"
        @dragover.prevent="isDragging = true"
        @dragleave.prevent="isDragging = false"
        @drop.prevent="onFileDrop">
        
        <div class="text-center">
          <v-icon size="64" color="grey-darken-1" class="mb-2">mdi-cloud-upload-outline</v-icon>
          <div class="text-h6 mb-2">Drag and Drop your Signature</div>
          <div class="text-body-2 mb-4">supported Formats : PNG,JPG,SVG(Max Size: 5MB)</div>
          
          <v-btn color="black" variant="flat" class="px-6" @click="$refs.fileInput.click()">
            Choose File
          </v-btn>
          
          <input 
            type="file" 
            ref="fileInput" 
            @change="onFileSelected" 
            accept=".png,.jpg,.jpeg,.svg" 
            class="hidden-input"
          />
        </div>
      </v-card>
      
      <!-- Preview uploaded signature if any -->
      <v-card v-if="uploadedSignature" outlined class="mb-8 pa-4">
        <div class="d-flex justify-space-between align-center mb-2">
          <div class="text-subtitle-1">Uploaded Signature</div>
          <v-btn icon="mdi-close" size="small" variant="text" @click="removeUploadedSignature"></v-btn>
        </div>
        <div class="text-center">
          <img :src="uploadedSignature" alt="Uploaded Signature" class="signature-preview" />
        </div>
      </v-card>
      
      <!-- Draw Signature Section -->
      <h2 class="text-h5 mb-3">Draw Your Signature</h2>
      
      <v-card outlined class="signature-pad-container mb-4">
        <!-- This div will be used for the signature pad integration -->
        <div ref="signaturePadContainer" class="signature-pad-area">
          <!-- External signature pad will be initialized here -->
          <div v-if="!hasSignature" class="signature-pad-placeholder" @click="initializeSignaturePad">
            Click Here to Start Drawing
          </div>
          
          <!-- Display captured signature -->
          <img v-if="capturedSignature" :src="capturedSignature" alt="Drawn Signature" class="captured-signature" />
        </div>
      </v-card>
      
      <div class="d-flex justify-space-between mb-8">
        <v-btn color="error" variant="outlined" @click="clearSignature">
          Clear
        </v-btn>
        
        <!-- Any additional control buttons for the signature pad can go here -->
      </div>
      
      <!-- Save Button -->
      <div class="text-center">
        <v-btn 
          color="success" 
          size="large" 
          min-width="240"
          :disabled="!canSave"
          :loading="isSaving"
          @click="saveSignature"
        >
          Save Signature
        </v-btn>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, computed } from 'vue';
  
  // State variables
  const isDragging = ref(false);
  const uploadedSignature = ref(null);
  const capturedSignature = ref(null);
  const hasSignature = ref(false);
  const isSaving = ref(false);
  const signaturePadContainer = ref(null);
  
  // Mock signature pad instance - replace with actual implementation
  const signaturePad = ref(null);
  
  // Computed property to determine if save is possible
  const canSave = computed(() => {
    return uploadedSignature.value || capturedSignature.value;
  });
  
  onMounted(() => {
    // Any initialization code that needs to run when component mounts
  });
  
  // File upload handlers
  const onFileSelected = (event) => {
    const file = event.target.files[0];
    if (file) {
      if (file.size > 5 * 1024 * 1024) {
        alert('File size exceeds the limit of 5MB');
        return;
      }
      
      if (!['image/png', 'image/jpeg', 'image/svg+xml'].includes(file.type)) {
        alert('Only PNG, JPG, and SVG files are supported');
        return;
      }
      
      const reader = new FileReader();
      reader.onload = (e) => {
        uploadedSignature.value = e.target.result;
      };
      reader.readAsDataURL(file);
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
      
      if (!['image/png', 'image/jpeg', 'image/svg+xml'].includes(file.type)) {
        alert('Only PNG, JPG, and SVG files are supported');
        return;
      }
      
      const reader = new FileReader();
      reader.onload = (e) => {
        uploadedSignature.value = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  };
  
  const removeUploadedSignature = () => {
    uploadedSignature.value = null;
  };
  
  // ====================================================
  // SIGNATURE PAD INTEGRATION METHODS
  // ====================================================
  
  /**
   * This is the method that will be called when the user clicks "Click Here to Start Drawing"
   * To Add your signature pad initialization code here
   */
  const initializeSignaturePad = () => {
    console.log('Initializing signature pad...');
    
    // SIGNATURE PAD INTEGRATION CODE GOES HERE
    // =========================================
    // 1. Initialize  signature pad library
    // 2. Set up event listeners for the pad
    // 3. Connect to  external signature device
    // 
    // Example with a hypothetical signature pad library:
    // 
    // import SignaturePad from 'your-signature-pad-library';
    // 
    // signaturePad.value = new SignaturePad({
    //   element: signaturePadContainer.value,
    //   onConnect: () => {
    //     console.log('Signature pad connected!');
    //     hasSignature.value = true;
    //   },
    //   onSignatureCapture: (signatureData) => {
    //     capturedSignature.value = signatureData;
    //   }
    // });
    // 
    // signaturePad.value.connect();
    
    // MOCK IMPLEMENTATION FOR DEMONSTRATION
    // This would be replaced with your actual implementation
    setTimeout(() => {
      hasSignature.value = true;
      // Mock signature data (base64 encoded image)
      capturedSignature.value = 'data:image/png;base64,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';
    }, 100);
  };
  
  /**
   * Clear the signature pad
   */
  const clearSignature = () => {
    // SIGNATURE PAD CLEAR CODE GOES HERE
    // ==================================
    // Example:
    // signaturePad.value.clear();
    
    capturedSignature.value = null;
    hasSignature.value = false;
  };
  
  // Save the signature (drawn or uploaded)
  const saveSignature = async () => {
    if (!canSave.value) {
      alert('Please upload or draw a signature first');
      return;
    }
    
    isSaving.value = true;
    
    try {
      let signatureData;
      
      if (uploadedSignature.value) {
        // Use the uploaded signature
        signatureData = uploadedSignature.value;
      } else {
        // Use the drawn signature
        signatureData = capturedSignature.value;
      }
      
      // Here you would normally send the signature to your backend
      console.log('Signature data:', signatureData.substring(0, 50) + '...');
      
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 1500));
      
      alert('Signature saved successfully!');
      
      // Optionally, you might want to navigate or clear the form
      // router.push('/next-step');
    } catch (error) {
      console.error('Error saving signature:', error);
      alert('An error occurred while saving the signature. Please try again.');
    } finally {
      isSaving.value = false;
    }
  };
  </script>
  
  <style scoped>
  .signature-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
  }
  
  .hidden-input {
    display: none;
  }
  
  .upload-area {
    border: 1px dashed #ccc;
    transition: background-color 0.3s, border-color 0.3s;
    min-height: 200px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .upload-area:hover {
    background-color: #f9f9f9;
    border-color: #999;
  }
  
  .active-drag {
    background-color: #f0f7ff;
    border-color: #2196F3;
  }
  
  .signature-preview {
    max-width: 100%;
    max-height: 150px;
    object-fit: contain;
  }
  
  .signature-pad-container {
    width: 100%;
    height: 300px;
    overflow: hidden;
    position: relative;
  }
  
  .signature-pad-area {
    width: 100%;
    height: 100%;
    position: relative;
    border: 1px solid #eee;
    background-color: #fff;
  }
  
  .signature-pad-placeholder {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;
    color: #888;
    cursor: pointer;
    background-color: #fafafa;
  }
  
  .captured-signature {
    max-width: 100%;
    max-height: 100%;
    object-fit: contain;
  }
  </style>