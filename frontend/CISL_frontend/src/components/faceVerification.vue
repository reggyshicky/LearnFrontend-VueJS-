<template>
  <v-card class="pa-4" elevation="1" rounded="lg" max-width="900px" style="margin: 0 auto;">
    <div class="face-scan-container">
      <!-- Face detection area -->
      <div class="scan-frame-area">
        <div v-if="!isCameraActive && !isProcessing" class="placeholder-content">
          <v-icon size="100" color="black">mdi-emoticon-outline</v-icon>
          <h2 class="text-center mt-4">Position Your Face within the Frame</h2>
        </div>

        <div v-if="isProcessing" class="processing-content">
          <v-progress-circular indeterminate size="80" color="primary"></v-progress-circular>
          <h2 class="text-center mt-4">Processing...</h2>
        </div>

        <video
          v-show="isCameraActive && !isProcessing"
          ref="videoElement"
          autoplay
          playsinline
          class="camera-feed"
        ></video>

        <canvas ref="canvasElement" style="display: none;"></canvas>

        <!-- Face scan overlay -->
        <div v-if="isCameraActive && !isProcessing" class="face-scan-overlay">
          <div class="scan-circle"></div>
        </div>
      </div>

      <!-- Action button -->
      <div class="text-center mt-6">
        <v-btn
          color="success"
          size="large"
          min-width="250"
          rounded="pill"
          prepend-icon="mdi-camera"
          :loading="isProcessing"
          :disabled="isProcessing"
          @click="handleScanAction"
        >
          {{ isCameraActive ? 'Capture' : 'Start Face Scan' }}
        </v-btn>
      </div>
    </div>

    <!-- Success dialog -->
    <v-dialog v-model="showSuccessDialog" max-width="400">
      <v-card>
        <v-card-title class="text-h5 bg-success text-white">Success</v-card-title>
        <v-card-text class="pa-4 text-body-1">
          <div class="text-center my-4">
            <v-icon size="64" color="success">mdi-check-circle</v-icon>
          </div>
          <p class="text-center">Face scan completed successfully!</p>
          <p class="text-center">Your identity has been verified.</p>
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

    <!-- Error dialog -->
    <v-dialog v-model="showErrorDialog" max-width="400">
      <v-card>
        <v-card-title class="text-h5 bg-error text-white">Error</v-card-title>
        <v-card-text class="pa-4 text-body-1">
          <div class="text-center my-4">
            <v-icon size="64" color="error">mdi-alert-circle</v-icon>
          </div>
          <p class="text-center">{{ errorMessage }}</p>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            variant="text"
            @click="showErrorDialog = false"
          >
            Try Again
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

// Refs for DOM elements
const videoElement = ref(null);
const canvasElement = ref(null);

// State management
const isCameraActive = ref(false);
const isProcessing = ref(false);
const showSuccessDialog = ref(false);
const showErrorDialog = ref(false);
const errorMessage = ref('');
const stream = ref(null);

// Start or capture face
const handleScanAction = async () => {
  if (!isCameraActive.value) {
    // Start camera
    await startCamera();
  } else {
    // Capture image and process
    captureFace();
  }
};

// Start camera function
const startCamera = async () => {
  try {
    stream.value = await navigator.mediaDevices.getUserMedia({
      video: {
        facingMode: 'user',
        width: { ideal: 1280 },
        height: { ideal: 720 }
      }
    });

    if (videoElement.value) {
      videoElement.value.srcObject = stream.value;
      isCameraActive.value = true;
    }
  } catch (error) {
    console.error('Error accessing camera:', error);
    errorMessage.value = 'Could not access the camera. Please ensure you have granted camera permissions.';
    showErrorDialog.value = true;
  }
};

// Capture face
const captureFace = () => {
  if (!videoElement.value || !canvasElement.value) return;

  isProcessing.value = true;

  // Set canvas dimensions to match video
  const video = videoElement.value;
  const canvas = canvasElement.value;
  canvas.width = video.videoWidth;
  canvas.height = video.videoHeight;

  // Draw current video frame to canvas
  const context = canvas.getContext('2d');
  context.drawImage(video, 0, 0, canvas.width, canvas.height);

  // Get image data from canvas
  const imageData = canvas.toDataURL('image/jpeg');

  // Simulate face detection API call
  simulateFaceDetection(imageData);
};

// Simulate face detection process
const simulateFaceDetection = (imageData) => {
  // In a real application, i should send this image to your backend API
  // const formData = new FormData();
  // formData.append('faceImage', dataURItoBlob(imageData));
  // await fetch('/api/verify-face', { method: 'POST', body: formData });

  // For demo, i'll simulate processing with a timeout
  setTimeout(() => {
    // Random success (80% chance) or failure
    const isSuccess = Math.random() < 0.8;

    if (isSuccess) {
      showSuccessDialog.value = true;
    } else {
      errorMessage.value = 'Face not clearly visible. Please ensure good lighting and position your face within the frame.';
      showErrorDialog.value = true;
    }

    isProcessing.value = false;
    stopCamera();
  }, 2000);
};

// Stop camera
const stopCamera = () => {
  if (stream.value) {
    stream.value.getTracks().forEach(track => track.stop());
    stream.value = null;
  }
  isCameraActive.value = false;
};

// Proceed to next page
const proceedToNextPage = () => {
  showSuccessDialog.value = false;
  // Navigate to next page
  router.replace('/personalInformation');

  // For demo, we'll reset
  isCameraActive.value = false;
  isProcessing.value = false;
};

// Helper function to convert data URI to Blob (for actual API upload)
const dataURItoBlob = (dataURI) => {
  const byteString = atob(dataURI.split(',')[1]);
  const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
  const ab = new ArrayBuffer(byteString.length);
  const ia = new Uint8Array(ab);

  for (let i = 0; i < byteString.length; i++) {
    ia[i] = byteString.charCodeAt(i);
  }

  return new Blob([ab], { type: mimeString });
};

// Clean up on component unmount
onUnmounted(() => {
  stopCamera();
});
</script>

<style scoped>
.face-scan-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.scan-frame-area {
  position: relative;
  width: 100%;
  height: 400px;
  background-color: #e0e0e0;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

.placeholder-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.processing-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.camera-feed {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.face-scan-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  pointer-events: none;
}

.scan-circle {
  width: 250px;
  height: 250px;
  border-radius: 50%;
  border: 3px solid #4CAF50;
  box-shadow: 0 0 0 2000px rgba(0, 0, 0, 0.3);
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(76, 175, 80, 0.4), 0 0 0 2000px rgba(0, 0, 0, 0.3);
  }
  70% {
    box-shadow: 0 0 0 15px rgba(76, 175, 80, 0), 0 0 0 2000px rgba(0, 0, 0, 0.3);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(76, 175, 80, 0), 0 0 0 2000px rgba(0, 0, 0, 0.3);
  }
}
</style>
