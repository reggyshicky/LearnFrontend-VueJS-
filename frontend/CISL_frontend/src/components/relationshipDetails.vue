<template>
  <div class="relationship-bank-container">
    <h2>Relationship & Bank information</h2>

    <!-- Relationship details form -->
    <div class="section">
      <h3>Relationship details</h3>
      <v-row>
        <v-col cols="12" sm="2">
          <label>Salutation</label>
          <select v-model="currentRelationship.salutation">
            <option value="MR">MR</option>
            <option value="MRS">MRS</option>
            <option value="MS">MS</option>
            <option value="DR">DR</option>
          </select>
        </v-col>
        <v-col cols="12" sm="3">
          <label>First Name</label>
          <input type="text" v-model="currentRelationship.firstName" />
        </v-col>
        <v-col cols="12" sm="3">
          <label>Middlename</label>
          <input type="text" v-model="currentRelationship.middleName" />
        </v-col>
        <v-col cols="12" sm="4">
          <label>LastName</label>
          <input type="text" v-model="currentRelationship.lastName" />
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12" sm="5">
          <label>Relationship</label>
          <input type="text" v-model="currentRelationship.relationshipType" placeholder="e.g. Mother, Father, Spouse" />
        </v-col>
        <v-col cols="12" sm="5">
          <label>Mobile Number</label>
          <input type="text" v-model="currentRelationship.mobileNumber" />
        </v-col>
        <v-col cols="12" sm="2" class="d-flex align-end">
          <button class="btn-add" @click="addRelationship">+ ADD</button>
        </v-col>
      </v-row>
    </div>

    <!-- Relationship list section -->
    <div v-if="relationships.length > 0" class="relationship-list section">
      <h3>Added Relationships</h3>
      <div class="relationship-card" v-for="(relation, index) in relationships" :key="index">
        <v-row>
          <v-col cols="10">
            <div><strong>{{ relation.relationshipType }}:</strong> {{ relation.salutation }} {{ relation.firstName }} {{ relation.middleName }} {{ relation.lastName }}</div>
            <div>Mobile: {{ relation.mobileNumber }}</div>
          </v-col>
          <v-col cols="2" class="d-flex justify-end align-center">
            <button class="btn-remove" @click="removeRelationship(index)">Remove</button>
          </v-col>
        </v-row>
      </div>
    </div>

    <!-- Bank information form -->
    <div class="section">
      <h3>Bank information</h3>
      <v-row>
        <v-col cols="12" sm="3">
          <label>Bank name</label>
          <input type="text" v-model="bankDetails.bankName" />
        </v-col>
        <v-col cols="12" sm="3">
          <label>Branch</label>
          <input type="text" v-model="bankDetails.branch" />
        </v-col>
        <v-col cols="12" sm="3">
          <label>Account Name</label>
          <input type="text" v-model="bankDetails.accountName" />
        </v-col>
        <v-col cols="12" sm="3">
          <label>Account Number</label>
          <input type="text" v-model="bankDetails.accountNumber" />
        </v-col>
      </v-row>
    </div>

    <div class="form-actions">
      <button class="btn-submit" @click="submitForm">SUBMIT</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router'

const router = useRouter()

// State for current relationship being added
const currentRelationship = ref({
  salutation: 'MR',
  firstName: '',
  middleName: '',
  lastName: '',
  relationshipType: '',
  mobileNumber: ''
});

// State for relationships list
const relationships = ref([]);

// State for bank details
const bankDetails = ref({
  bankName: '',
  branch: '',
  accountName: '',
  accountNumber: ''
});

// Add relationship to the list
const addRelationship = () => {
  // Simple validation
  if (!currentRelationship.value.firstName || !currentRelationship.value.lastName || !currentRelationship.value.relationshipType) {
    alert('Please fill in at least First Name, Last Name and Relationship Type');
    return;
  }

  // Add to relationships array
  relationships.value.push({...currentRelationship.value});

  // Reset form for next entry
  currentRelationship.value = {
    salutation: 'MR',
    firstName: '',
    middleName: '',
    lastName: '',
    relationshipType: '',
    mobileNumber: ''
  };
};

// Remove relationship from the list
const removeRelationship = (index) => {
  relationships.value.splice(index, 1);
};

// Submit all data
const submitForm = () => {
  // Validate bank information
  if (!bankDetails.value.bankName || !bankDetails.value.accountNumber) {
    alert('Please fill in at least Bank Name and Account Number');
    return;
  }

  // Prepare data for submission
  const formData = {
    relationships: relationships.value,
    bankDetails: bankDetails.value
  };

  // For demonstration - log the data
  console.log('Submitting form data:', formData);

  // Mock API call
  setTimeout(() => {
    alert('Form submitted successfully!');
    // with back, you might redirect here
    router.replace('/taxExemption');
  }, 1000);
};
</script>

<style scoped>
.relationship-bank-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

.section {
  background-color: #f9f9f9;
  padding: 15px;
  margin-bottom: 20px;
  border-radius: 5px;
}

h2 {
  color: #333;
  margin-bottom: 20px;
}

h3 {
  color: #444;
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  font-size: 0.9em;
}

input, select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

.btn-add {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 8px 15px;
  border-radius: 4px;
  cursor: pointer;
  height: 38px;
}

.btn-submit {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1em;
  display: block;
  margin: 0 auto;
}

.relationship-list {
  margin-top: 20px;
}

.relationship-card {
  background-color: white;
  padding: 10px 15px;
  margin-bottom: 10px;
  border-radius: 4px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

.btn-remove {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.form-actions {
  margin-top: 30px;
  text-align: center;
}

/* Utility classes for flex alignment */
.d-flex {
  display: flex;
}

.align-end {
  align-items: flex-end;
}

.align-center {
  align-items: center;
}

.justify-end {
  justify-content: flex-end;
}
</style>
