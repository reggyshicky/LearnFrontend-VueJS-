<template>
  <v-card class="pa-4" elevation="1" rounded="lg" max-width="1000px" style="margin: 0 auto;">
    <v-form ref="form" v-model="isFormValid" @submit.prevent="submitForm">
      <!-- Personal Information Section -->
      <div class="section-container mb-6">
        <h2 class="text-h6 font-weight-bold mb-4">Personal Information</h2>

        <v-row>
          <v-col cols="12" sm="2">
            <div class="text-subtitle-2 mb-1">Salutation</div>
            <v-select
              v-model="formData.salutation"
              :items="salutationOptions"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              hide-details="auto"
              class="rounded-lg"
            ></v-select>
          </v-col>

          <v-col cols="12" sm="3">
            <div class="text-subtitle-2 mb-1">First Name</div>
            <v-text-field
              v-model="formData.firstName"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              :readonly="autoPopulatedFields.firstName"
              hide-details="auto"
              class="rounded-lg"
            ></v-text-field>
          </v-col>

          <v-col cols="12" sm="3">
            <div class="text-subtitle-2 mb-1">Middlename</div>
            <v-text-field
              v-model="formData.middleName"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              :readonly="autoPopulatedFields.middleName"
              hide-details="auto"
              class="rounded-lg"
            ></v-text-field>
          </v-col>

          <v-col cols="12" sm="4">
            <div class="text-subtitle-2 mb-1">LastName</div>
            <v-text-field
              v-model="formData.surname"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              :readonly="autoPopulatedFields.surname"
              hide-details="auto"
              class="rounded-lg"
            ></v-text-field>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="12" sm="4">
            <div class="text-subtitle-2 mb-1">DOB</div>
            <v-text-field
              v-model="formattedDate"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              placeholder="YYYY-MM-DD"
              hide-details="auto"
              class="rounded-lg"
              readonly
              :rules="dobRules"
              @click="openDatePicker = true"
            >
              <template v-slot:append>
                <v-menu
                  v-model="openDatePicker"
                  :close-on-content-click="false"
                  transition="scale-transition"
                  min-width="auto"
                >
                  <template v-slot:activator="{ props }">
                    <v-icon v-bind="props">mdi-calendar</v-icon>
                  </template>
                  <v-date-picker
                    v-model="selectedDate"
                    @update:model-value="handleDateChange"
                  ></v-date-picker>
                </v-menu>
              </template>
            </v-text-field>
          </v-col>
          <v-col cols="12" sm="4">
            <div class="text-subtitle-2 mb-1">Gender</div>
            <v-select
              v-model="formData.gender"
              :items="genderOptions"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              :rules="requiredRules"
              hide-details="auto"
              class="rounded-lg"
            ></v-select>
          </v-col>

          <v-col cols="12" sm="4">
            <div class="text-subtitle-2 mb-1">Nationality</div>
            <v-select
              v-model="formData.nationality"
              :items="countryOptions"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              :rules="requiredRules"
              hide-details="auto"
              class="rounded-lg"
            ></v-select>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="12" sm="6">
            <div class="text-subtitle-2 mb-1">Identity Number</div>
            <v-text-field
              v-model="formData.idNumber"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              :readonly="autoPopulatedFields.idNumber"
              hide-details="auto"
              class="rounded-lg"
            ></v-text-field>
          </v-col>
        </v-row>
      </div>

      <!-- Contact Details Section -->
      <div class="section-container mb-6">
        <h2 class="text-h6 font-weight-bold mb-4">Contact Details</h2>

        <v-row>
          <v-col cols="12" sm="3">
            <div class="text-subtitle-2 mb-1">Email ID</div>
            <v-text-field
              v-model="formData.email"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              :rules="emailRules"
              hide-details="auto"
              class="rounded-lg"
            ></v-text-field>
          </v-col>

          <v-col cols="12" sm="3">
            <div class="text-subtitle-2 mb-1">Phone Number</div>
            <v-text-field
              v-model="formData.phoneNumber"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              :readonly="autoPopulatedFields.phoneNumber"
              hide-details="auto"
              class="rounded-lg"
            ></v-text-field>
          </v-col>

          <v-col cols="12" sm="3">
            <div class="text-subtitle-2 mb-1">Alternative Number</div>
            <v-text-field
              v-model="formData.alternativePhone"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              :rules="phoneRules"
              hide-details="auto"
              class="rounded-lg"
            ></v-text-field>
          </v-col>

          <v-col cols="12" sm="3">
            <div class="text-subtitle-2 mb-1">Postal Address</div>
            <v-text-field
              v-model="formData.postalAddress"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              hide-details="auto"
              class="rounded-lg"
            ></v-text-field>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="12">
            <div class="text-subtitle-2 mb-1">Physical Address</div>
            <v-text-field
              v-model="formData.physicalAddress"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              :rules="requiredRules"
              hide-details="auto"
              class="rounded-lg"
            ></v-text-field>
          </v-col>
        </v-row>
      </div>

      <!-- Employment Details Section -->
      <div class="section-container">
        <h2 class="text-h6 font-weight-bold mb-4">Employment Details</h2>

        <v-row>
          <v-col cols="12" sm="4">
            <div class="text-subtitle-2 mb-1">Occupation</div>
            <v-text-field
              v-model="formData.occupation"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              :rules="requiredRules"
              hide-details="auto"
              class="rounded-lg"
            ></v-text-field>
          </v-col>

          <v-col cols="12" sm="4">
            <div class="text-subtitle-2 mb-1">Monthly income/Turn Over</div>
            <v-select
              v-model="formData.monthlyIncome"
              :items="incomeRanges"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              :rules="requiredRules"
              hide-details="auto"
              class="rounded-lg"
            ></v-select>
          </v-col>

          <v-col cols="12" sm="4">
            <div class="text-subtitle-2 mb-1">Source of Funds</div>
            <v-select
              v-model="formData.sourceOfFunds"
              :items="sourceOfFundsOptions"
              variant="outlined"
              density="comfortable"
              bg-color="#f0f0f0"
              :rules="requiredRules"
              hide-details="auto"
              class="rounded-lg"
            ></v-select>
          </v-col>
        </v-row>
      </div>

      <!-- Submit Button -->
      <v-row class="mt-6">
        <v-col cols="12" class="text-center">
          <v-btn
            type="submit"
            color="success"
            size="large"
            min-width="200"
            :loading="loading"
            :disabled="!isFormValid"
            class="px-6"
          >
            SUBMIT
          </v-btn>
        </v-col>
      </v-row>
    </v-form>

    <!-- Loading overlay for IPRS check -->
    <v-dialog v-model="isIprsLoading" persistent width="300">
      <v-card color="primary" dark>
        <v-card-text>
          <p class="text-center mb-0">Fetching your information...</p>
          <v-progress-linear indeterminate color="white" class="mb-0"></v-progress-linear>
        </v-card-text>
      </v-card>
    </v-dialog>

    <!-- Success dialog -->
    <v-dialog v-model="showSuccessDialog" max-width="400">
      <v-card>
        <v-card-title class="text-h5 bg-success text-white">Success</v-card-title>
        <v-card-text class="pa-4 text-body-1">
          Your personal information has been submitted successfully.
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
import { ref, reactive, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const form = ref(null);
const isFormValid = ref(false);
const loading = ref(false);
const showSuccessDialog = ref(false);
const isIprsLoading = ref(false);
const openDatePicker = ref(false);
const selectedDate = ref(null)
const formattedDate = ref('')

// Date Change Handler
const handleDateChange = (date) => {
  selectedDate.value = date
  formattedDate.value = formatDate(date)
  formData.dob = formattedDate.value

  openDatePicker.value = false
}

// Format Date to 'YYYY-MM-DD'
const formatDate = (date) => {
  const d = new Date(date)
  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

// Track which fields were auto-populated
const autoPopulatedFields = reactive({
  firstName: false,
  middleName: false,
  surname: false,
  idNumber: false,
  phoneNumber: false,
  kraPinNumber: false
});

// Form data with previously entered information (if available)
const formData = reactive({
  // Personal Info
  salutation: '',
  firstName: '',
  middleName: '',
  surname: '',
  dob: '',
  gender: '',
  nationality: 'Kenya',
  idNumber: '',

  // Contact Info
  email: '',
  phoneNumber: '',
  alternativePhone: '',
  postalAddress: '',
  physicalAddress: '',

  // Employment Info
  occupation: '',
  monthlyIncome: '',
  sourceOfFunds: ''
});

// Options for dropdowns
const salutationOptions = ['Mr', 'Mrs', 'Miss', 'Dr', 'Prof'];
const genderOptions = ['Male', 'Female', 'Other'];
const countryOptions = ['Kenya', 'Uganda', 'Tanzania', 'Rwanda', 'Burundi', 'South Sudan', 'Ethiopia', 'Somalia'];
const incomeRanges = [
  'Less than KES 10,000',
  'KES 10,000 - KES 50,000',
  'KES 51,000 - KES 100,000',
  'KES 101,000 - KES 200,000',
  'KES 201,000 - KES 500,000',
  'KES 501,000 - KES 1,000,000',
  'Above KES 1,000,000'
];
const sourceOfFundsOptions = [
  'Salary',
  'Business Income',
  'Investment Returns',
  'Savings',
  'Inheritance',
  'Pension',
  'Other'
];

// Validation rules
const requiredRules = [
  v => !!v || 'This field is required'
];

const emailRules = [
  v => !!v || 'E-mail is required',
  v => /.+@.+\..+/.test(v) || 'E-mail must be valid'
];

const phoneRules = [
  v => !v || /^0[7-9][0-9]{8}$/.test(v) || 'Enter a valid Kenyan phone number'
];

const dobRules = [
  v => !!v || 'Date of birth is required'
];

// Load previously entered data and attempt IPRS lookup
onMounted(async () => {
  // First, attempt to load data from localStorage (previously entered)
  const previousData = localStorage.getItem('basicInfo');

  if (previousData) {
    try {
      const parsedData = JSON.parse(previousData);
      console.log('Loaded data from localStorage:', parsedData);

      // Auto-populate fields from previous data
      if (parsedData.first_name) {
        formData.firstName = parsedData.first_name;
        autoPopulatedFields.firstName = true;
      }

      if (parsedData.middle_name) {
        formData.middleName = parsedData.middle_name;
        autoPopulatedFields.middleName = true;
      }

      if (parsedData.sur_name) {
        formData.surname = parsedData.sur_name;
        autoPopulatedFields.surname = true;
      }

      if (parsedData.id_number) {
        formData.idNumber = parsedData.id_number;
        autoPopulatedFields.idNumber = true;

        // Now attempt to get additional data from IPRS using ID number
        tryFetchIprsData(parsedData.id_number);
      }

      if (parsedData.phone_number) {
        formData.phoneNumber = parsedData.phone_number;
        autoPopulatedFields.phoneNumber = true;
      }

      if (parsedData.kra_pin_number) {
        autoPopulatedFields.kraPinNumber = true;
      }
    } catch (error) {
      console.error('Error parsing localStorage data:', error);
    }
  } else {
    console.log('No previous data found in localStorage');
  }
});

// Function to attempt fetching data from IPRS
const tryFetchIprsData = async (idNumber) => {
  // Only attempt IPRS fetch if we have an ID number
  if (!idNumber) return;

  isIprsLoading.value = true;

  try {
    // In a real application, this would be an API call
    // For demo purposes, we'll simulate this with a timeout
    await new Promise(resolve => setTimeout(resolve, 2000));

    // For demo, simulate successful IPRS fetch 50% of the time
    const iprsAvailable = Math.random() > 0.5;

    if (iprsAvailable) {
      // Simulate data from IPRS
      formData.dob = '1990-01-15';
      formData.gender = 'Male';
      formData.physicalAddress = 'Nairobi, Kenya';
      formData.postalAddress = 'P.O. Box 12345-00100, Nairobi';
      console.log('IPRS data fetched successfully');
    } else {
      console.log('IPRS data not available, user will need to fill manually');
    }
  } catch (error) {
    console.error('Error fetching IPRS data:', error);
  } finally {
    isIprsLoading.value = false;
  }
};

// Submit form
const submitForm = async () => {
  if (!isFormValid.value) {
    return;
  }

  loading.value = true;

  try {
    // In a real application, this would call your API
    // For now, we'll simulate an API call with a timeout
    await new Promise(resolve => setTimeout(resolve, 1500));

    // Save all data in localStorage for later use
    const completeFormData = {
      ...formData,
      // Add any other data you want to store
    };

    localStorage.setItem('completeFormData', JSON.stringify(completeFormData));
    console.log('Form data saved to localStorage:', completeFormData);

    // Show success dialog
    showSuccessDialog.value = true;
  } catch (error) {
    console.error('Submission error:', error);
    // You would handle errors here in a real application
  } finally {
    loading.value = false;
  }
};

// Navigate to the next page
const proceedToNextPage = () => {
  showSuccessDialog.value = false;
  // with backend, navigate to the next page
  router.replace('/relationshipDetails');
};
</script>

<style scoped>
.section-container {
  border-bottom: 1px solid #e0e0e0;
  padding-bottom: 16px;
}

:deep(.v-field__input) {
  padding-top: 8px !important;
  padding-bottom: 8px !important;
}
</style>
