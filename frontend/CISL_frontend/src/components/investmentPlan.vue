<template>
  <div class="investment-plan-container">
    <h1>Choose Investment Plan</h1>

    <v-card class="pa-4">
      <p class="text-subtitle-1 font-weight-medium mb-4">
        Select Money market Instrument and Investment Frequency
      </p>

      <v-row>
        <!-- Money Market Instruments Column -->
        <v-col cols="12" md="6">
          <v-card outlined class="instrument-container">
            <v-card-title class="pb-0">Money Market Instruments</v-card-title>

            <v-card-text>
              <v-radio-group v-model="selectedInstrument">
                <v-sheet
                  v-for="instrument in instruments"
                  :key="instrument.id"
                  class="mb-2 pa-2 instrument-item"
                  rounded
                  outlined
                >
                  <v-radio :value="instrument.id">
                    <template v-slot:label>
                      <div>
                        <div class="text-subtitle-1">{{ instrument.name }}</div>
                        <div class="text-caption text-grey">
                          Expected yield {{ instrument.yield }} {{ instrument.yieldType }}
                        </div>
                      </div>
                    </template>
                  </v-radio>
                </v-sheet>

                <v-progress-circular
                  v-if="loadingInstruments"
                  indeterminate
                  color="primary"
                  class="mt-4"
                ></v-progress-circular>
              </v-radio-group>
            </v-card-text>
          </v-card>
        </v-col>

        <!-- Investment Frequency Column -->
        <v-col cols="12" md="6">
          <v-card outlined class="frequency-container">
            <v-card-title class="pb-0">Investment Frequency</v-card-title>

            <v-card-text>
              <v-radio-group v-model="selectedFrequency">
                <v-sheet
                  v-for="frequency in frequencies"
                  :key="frequency.id"
                  class="mb-2 pa-2 frequency-item"
                  rounded
                  outlined
                >
                  <v-radio :value="frequency.id">
                    <template v-slot:label>
                      <div class="text-subtitle-1">{{ frequency.name }}</div>
                    </template>
                  </v-radio>
                </v-sheet>
              </v-radio-group>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <v-row class="mt-6">
        <v-col cols="12" class="d-flex justify-center">
          <v-btn
            color="success"
            size="large"
            min-width="120"
            :disabled="!isFormValid || submitting"
            :loading="submitting"
            @click="submitPlan"
          >
            Submit
          </v-btn>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter()

// State variables
const selectedInstrument = ref(null);
const selectedFrequency = ref(null);
const loadingInstruments = ref(false);
const submitting = ref(false);

// Default frequencies (these likely won't change often)
const frequencies = ref([
  { id: 'daily', name: 'Daily Investment' },
  { id: 'weekly', name: 'Weekly Investment' },
  { id: 'monthly', name: 'Monthly Investment' }
]);

// Instruments will be fetched from API
const instruments = ref([]);

// Form validation
const isFormValid = computed(() => {
  return selectedInstrument.value && selectedFrequency.value;
});

// Fetch instruments from API
const fetchInstruments = async () => {
  loadingInstruments.value = true;

  try {
    // integrate actual api
    // await axios.get('/api/money-market-instruments')

    // Simulating API delay
    await new Promise(resolve => setTimeout(resolve, 1000));

    // Mock response data
    instruments.value = [
      {
        id: 'treasury-bills',
        name: 'Treasury bills',
        yield: '6%',
        yieldType: 'p.a.'
      },
      {
        id: 'treasury-bond',
        name: 'Treasury bond',
        yield: '9%',
        yieldType: 'per annum'
      },
      {
        id: 'call-deposit',
        name: 'Call deposit',
        yield: '7%',
        yieldType: 'per annum'
      },
      {
        id: 'fixed-deposit',
        name: 'Fixed Deposit',
        yield: '10%',
        yieldType: 'per annum'
      }
    ];
  } catch (error) {
    console.error('Error fetching instruments:', error);
    // Handle error - maybe set an error state or display a notification
  } finally {
    loadingInstruments.value = false;
  }
};

// Submit the investment plan
const submitPlan = async () => {
  if (!isFormValid.value) {
    alert('Please select both an instrument and a frequency');
    return;
  }

  submitting.value = true;

  try {
    // Prepare data for submission
    const planData = {
      instrumentId: selectedInstrument.value,
      frequencyId: selectedFrequency.value
    };

    console.log('Submitting plan data:', planData);

    // with backend
    // await axios.post('/api/investment-plans', planData)

    // Simulate API delay
    await new Promise(resolve => setTimeout(resolve, 1500));

    // Navigate to next page
    router.push('/investment-summary');
  } catch (error) {
    console.error('Error submitting plan:', error);
    alert('An error occurred while submitting your investment plan. Please try again.');
  } finally {
    submitting.value = false;
  }
};

// Fetch instruments on component mount
onMounted(() => {
  fetchInstruments();
});
</script>

<style scoped>
.investment-plan-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  color: #2c3e50;
  margin-bottom: 20px;
  border-bottom: 2px solid #eee;
  padding-bottom: 10px;
}

.instrument-item, .frequency-item {
  border: 1px solid rgba(0, 0, 0, 0.12);
  transition: background-color 0.2s;
}

.instrument-item:hover, .frequency-item:hover {
  background-color: #f5f5f5;
}

/* Add some space between cards on mobile */
@media (max-width: 960px) {
  .frequency-container {
    margin-top: 16px;
  }
}
</style>
