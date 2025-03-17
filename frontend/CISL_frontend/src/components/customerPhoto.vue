<template>
  <v-card class="pa-4" elevation="1" rounded="lg" max-width="900px" style="margin: 0 auto;">
    <v-card-title class="text-h5 font-weight-bold pb-4">ID Document</v-card-title>

    <v-row>
      <!-- Front Side Upload/Capture -->
      <v-col cols="12" md="6">
        <v-card class="id-card-container" flat>
          <div class="d-flex flex-column align-center pa-4">
            <v-icon size="60" color="black">mdi-account-card-details</v-icon>
            <div class="text-subtitle-1 font-weight-bold mt-2">Front Side</div>
            <div class="text-caption text-center my-2">
              upload or capture the front side of your ID
            </div>

            <div class="d-flex justify-center mt-2 gap-2">
              <v-btn
                color="primary"
                variant="outlined"
                prepend-icon="mdi-upload"
                class="upload-btn"
                rounded="pill"
                @click="triggerFileInput('front')"
              >
                upload
              </v-btn>

              <v-btn
                color="primary"
                variant="outlined"
                prepend-icon="mdi-camera"
                class="capture-btn"
                rounded="pill"
                @click="openCamera('front')"
              >
                capture
              </v-btn>
            </div>

            <!-- Hidden file input for front side -->
            <input
              type="file"
              ref="frontFileInput"
              accept="image/*"
              style="display: none"
              @change="handleFileUpload('front', $event)"
            />

            <!-- Preview for front side -->
            <v-img
              v-if="frontImage"
              :src="frontImage"
              width="100%"
              max-height="200"
              contain
              class="mt-4"
            ></v-img>
          </div>
        </v-card>
      </v-col>

      <!-- Back Side Upload/Capture -->
      <v-col cols="12" md="6">
        <v-card class="id-card-container" flat>
          <div class="d-flex flex-column align-center pa-4">
            <v-icon size="60" color="black">mdi-account-card-details</v-icon>
            <div class="text-subtitle-1 font-weight-bold mt-2">Back side</div>
            <div class="text-caption text-center my-2">
              upload or capture the back side of your ID
            </div>

            <div class="d-flex justify-center mt-2 gap-2">
              <v-btn
                color="primary"
                variant="outlined"
                prepend-icon="mdi-upload"
                class="upload-btn"
                rounded="pill"
                @click="triggerFileInput('back')"
              >
                upload
              </v-btn>

              <v-btn
                color="primary"
                variant="outlined"
                prepend-icon="mdi-camera"
                class="capture-btn"
                rounded="pill"
                @click="openCamera('back')"
              >
                capture
              </v-btn>
            </div>

            <!-- Hidden file input for back side -->
            <input
              type="file"
              ref="backFileInput"
              accept="image/*"
              style="display: none"
              @change="handleFileUpload('back', $event)"
            />

            <!-- Preview for back side -->
            <v-img
              v-if="backImage"
              :src="backImage"
              width="100%"
              max-height="200"
              contain
              class="mt-4"
            ></v-img>
          </div>
        </v-card>
      </v-col>
    </v-row>

    <!-- Camera Dialog -->
    <v-dialog v-model="cameraOpen" max-width="640">
      <v-card>
        <v-card-title class="text-h5">Capture ID {{ currentSide === 'front' ? 'Front' : 'Back' }} Side</v-card-title>
        <v-card-text>
          <div class="camera-container">
            <video ref="cameraFeed" autoplay playsinline width="100%"></video>
            <canvas ref="canvas" style="display: none"></canvas>
          </div>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="error" @click="closeCamera">Cancel</v-btn>
          <v-btn color="success" @click="captureImage">Capture</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Submit Button -->
    <v-row class="mt-6">
      <v-col cols="12" class="text-center">
        <v-btn
          color="success"
          size="large"
          min-width="200"
          :loading="loading"
          :disabled="!isFormValid"
          rounded="pill"
          @click="submitDocuments"
        >
          Submit
        </v-btn>
      </v-col>
    </v-row>

    <!-- Success dialog -->
    <v-dialog v-model="showSuccessDialog" max-width="400">
      <v-card>
        <v-card-title class="text-h5 bg-success text-white">Success</v-card-title>
        <v-card-text class="pa-4 text-body-1">
          Your ID documents have been submitted successfully. You will be redirected to the next page.
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="success"
            variant="text"
            @click="proceedToNextPage"
          >
            Continue
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const loading = ref(false)
const showSuccessDialog = ref(false)

// Image upload/capture state
const frontImage = ref(null)
const backImage = ref(null)
const frontFileInput = ref(null)
const backFileInput = ref(null)
const cameraFeed = ref(null)
const canvas = ref(null)
const cameraOpen = ref(false)
const currentSide = ref(null)
const stream = ref(null)

// Computed property to check if form is valid (both sides uploaded)
const isFormValid = computed(() => {
  return frontImage.value && backImage.value
})

// Trigger file input click
const triggerFileInput = (side) => {
  if (side === 'front') {
    frontFileInput.value.click()
  } else {
    backFileInput.value.click()
  }
}

// Handle file upload
const handleFileUpload = (side, event) => {
  const file = event.target.files[0]
  if (!file) return

  // Check file type and size
  if (!file.type.match('image.*')) {
    alert('Please upload an image file')
    return
  }

  if (file.size > 5 * 1024 * 1024) { // 5MB limit
    alert('File size should be less than 5MB')
    return
  }

  const reader = new FileReader()
  reader.onload = (e) => {
    if (side === 'front') {
      frontImage.value = e.target.result
    } else {
      backImage.value = e.target.result
    }
  }
  reader.readAsDataURL(file)
}

// Open camera
const openCamera = async (side) => {
  currentSide.value = side
  cameraOpen.value = true

  try {
    stream.value = await navigator.mediaDevices.getUserMedia({
      video: { facingMode: 'environment' }
    })

    if (cameraFeed.value) {
      cameraFeed.value.srcObject = stream.value
    }
  } catch (error) {
    console.error('Error accessing camera:', error)
    alert('Could not access the camera. Please ensure you have granted camera permissions.')
    cameraOpen.value = false
  }
}

// Close camera
const closeCamera = () => {
  if (stream.value) {
    stream.value.getTracks().forEach(track => track.stop())
    stream.value = null
  }
  cameraOpen.value = false
}

// Capture image from camera
const captureImage = () => {
  if (!canvas.value || !cameraFeed.value) return

  const context = canvas.value.getContext('2d')
  canvas.value.width = cameraFeed.value.videoWidth
  canvas.value.height = cameraFeed.value.videoHeight

  context.drawImage(
    cameraFeed.value,
    0,
    0,
    canvas.value.width,
    canvas.value.height
  )

  const imageDataUrl = canvas.value.toDataURL('image/jpeg')

  if (currentSide.value === 'front') {
    frontImage.value = imageDataUrl
  } else {
    backImage.value = imageDataUrl
  }

  closeCamera()
}

// Submit documents
const submitDocuments = async () => {
  if (!isFormValid.value) {
    alert('Please upload or capture both sides of your ID')
    return
  }

  loading.value = true

  try {
    // In a real application,call your API to upload the images
    // const formData = new FormData()
    // formData.append('frontImage', dataURItoBlob(frontImage.value))
    // formData.append('backImage', dataURItoBlob(backImage.value))
    // await fetch('/api/upload-documents', { method: 'POST', body: formData })

    // For now,  I simulate API call with timeout
    await new Promise(resolve => setTimeout(resolve, 1500))

    // Show success dialog
    showSuccessDialog.value = true
  } catch (error) {
    console.error('Submission error:', error)
    alert('There was an error submitting your documents. Please try again.')
  } finally {
    loading.value = false
  }
}

// Helper function to convert data URI to Blob (for actual API upload)
const dataURItoBlob = (dataURI) => {
  const byteString = atob(dataURI.split(',')[1])
  const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]
  const ab = new ArrayBuffer(byteString.length)
  const ia = new Uint8Array(ab)

  for (let i = 0; i < byteString.length; i++) {
    ia[i] = byteString.charCodeAt(i)
  }

  return new Blob([ab], { type: mimeString })
}

// Navigate to next page
const proceedToNextPage = () => {
  showSuccessDialog.value = false
  router.push('/faceVerification')

  // Reset form for demonstration
  frontImage.value = null
  backImage.value = null
}

// Clean up camera resources when component is unmounted
const onUnmounted = () => {
  closeCamera()
}
</script>

<style scoped>
.id-card-container {
  border: 1px solid #dedede;
  height: 100%;
  background-color: white;
}

.gap-2 {
  gap: 8px;
}

.camera-container {
  position: relative;
  width: 100%;
  height: 100%;
  background-color: #000;
}
</style>
