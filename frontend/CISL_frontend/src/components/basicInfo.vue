<template>
  <v-card class="pa-4" elevation="1" rounded="lg" max-width="900px" style="margin: 0 auto;">
    <v-form ref="form" v-model="isFormValid" @submit.prevent="submitForm">
      <v-card-title class="text-h5 font-weight-bold pb-2">Personal Information</v-card-title>

      <v-row>
        <v-col cols="12" md="4">
          <v-text-field
            v-model="formData.firstName"
            :rules="nameRules"
            label="First Name (as per ID)"
            variant="outlined"
            bg-color="#f0f0f0"
            density="comfortable"
            required
            hide-details="auto"
            class="rounded-lg"
          ></v-text-field>
        </v-col>

        <v-col cols="12" md="4">
          <v-text-field
            v-model="formData.middleName"
            label="Middlename"
            variant="outlined"
            bg-color="#f0f0f0"
            density="comfortable"
            hide-details="auto"
            class="rounded-lg"
          ></v-text-field>
        </v-col>

        <v-col cols="12" md="4">
          <v-text-field
            v-model="formData.surname"
            :rules="nameRules"
            label="Surname"
            variant="outlined"
            bg-color="#f0f0f0"
            density="comfortable"
            required
            hide-details="auto"
            class="rounded-lg"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12">
          <v-text-field
            v-model="formData.idNumber"
            :rules="idNumberRules"
            label="ID/Passport/Service ID"
            variant="outlined"
            bg-color="#f0f0f0"
            density="comfortable"
            required
            hide-details="auto"
            class="rounded-lg"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12" md="6">
          <v-text-field
            v-model="formData.phoneNumber"
            :rules="phoneNumberRules"
            label="Phone Number"
            variant="outlined"
            bg-color="#f0f0f0"
            density="comfortable"
            required
            hide-details="auto"
            class="rounded-lg"
            placeholder="e.g. 07XXXXXXXX"
          ></v-text-field>
        </v-col>

        <v-col cols="12" md="6">
          <v-text-field
            v-model="formData.kraPinNumber"
            :rules="kraPinRules"
            label="KRA PIN Number"
            variant="outlined"
            bg-color="#f0f0f0"
            density="comfortable"
            required
            hide-details="auto"
            class="rounded-lg"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row class="mt-6">
        <v-col cols="12" class="text-center">
          <v-btn
            type="submit"
            color="success"
            size="large"
            min-width="200"
            :loading="loading"
            :disabled="!isFormValid"
            rounded="pill"
          >
            SUBMIT
          </v-btn>
        </v-col>
      </v-row>
    </v-form>

    <!-- Success dialog -->
    <v-dialog v-model="showSuccessDialog" max-width="400">
      <v-card>
        <v-card-title class="text-h5 bg-success text-white">Success</v-card-title>
        <v-card-text class="pa-4 text-body-1">
          Your information has been submitted successfully. You will be redirected to the next page.
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
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const form = ref(null)
const isFormValid = ref(false)
const loading = ref(false)
const showSuccessDialog = ref(false)

// Form data
const formData = reactive({
  firstName: '',
  middleName: '',
  surname: '',
  idNumber: '',
  phoneNumber: '',
  kraPinNumber: ''
})

// Validation rules
const nameRules = [
  v => !!v || 'Name is required',
  v => (v && v.length >= 2) || 'Name must be at least 2 characters',
  v => (v && v.length <= 50) || 'Name must be less than 50 characters',
  v => (v && /^[a-zA-Z\s'-]+$/.test(v)) || 'Name must contain only letters, spaces, hyphens, and apostrophes'
]

const idNumberRules = [
  v => !!v || 'ID number is required',
  v => (v && v.length >= 5) || 'ID number must be at least 5 characters',
  v => (v && /^[a-zA-Z0-9-]+$/.test(v)) || 'ID number can only contain letters, numbers, and hyphens'
]

const phoneNumberRules = [
  v => !!v || 'Phone number is required',
  v => (v && /^0[7-9][0-9]{8}$/.test(v)) || 'Enter a valid Kenyan phone number starting with 07, 08, or 09 (10 digits)'
]

const kraPinRules = [
  v => !!v || 'KRA PIN is required',
  // v => (v && /^[A-Z][0-9]{9}[A-Z]$/.test(v)) || 'KRA PIN must be in the format A123456789B'
]

// Submit form
const submitForm = async () => {
  if (!isFormValid.value) {
    return
  }

  loading.value = true

  try {
    // Save form data to localStorage for the next component to access
    const dataToStore = {
      first_name: formData.firstName,
      middle_name: formData.middleName,
      sur_name: formData.surname,
      id_number: formData.idNumber,
      phone_number: formData.phoneNumber,
      kra_pin_number: formData.kraPinNumber
    }

    // Store in localStorage
    localStorage.setItem('basicInfo', JSON.stringify(dataToStore))

    // In a real application, this would call your API
    // For now, we'll simulate an API call with a timeout
    await new Promise(resolve => setTimeout(resolve, 1500))

    // Show success dialog
    showSuccessDialog.value = true
  } catch (error) {
    console.error('Submission error:', error)
    // You would handle errors here in a real application
  } finally {
    loading.value = false
  }
}

// Navigate to the next page
const proceedToNextPage = () => {
  showSuccessDialog.value = false
  // In a real application, navigate to the next page
  router.push('/customerPhoto')

  // Reset form for demonstration
  if (form.value) {
    form.value.reset()
  }
}
</script>

<style scoped>
.v-card {
  border: 1px solid #dedede;
}
</style>
