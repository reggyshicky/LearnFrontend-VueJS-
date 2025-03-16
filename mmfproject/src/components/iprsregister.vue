<template>
    <v-card>
      <v-card-item>
   
      <v-form ref="customerDetails" v-model="valid">
        <v-container v-if="currentForm == 1">
          <v-row>
            <v-col cols="12" md="4">
              <h4>Personal Information</h4>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12" md="4">
              <div class="text-subtitle-1 text-medium-emphasis">First name</div>
              <v-text-field v-model="customer.first_name" variant="outlined" density="compact" :rules="nameRules"
                required></v-text-field>
            </v-col>
   
            <v-col cols="12" md="4">
              <div class="text-subtitle-1 text-medium-emphasis">Last name</div>
   
              <v-text-field v-model="customer.middle_name" density="compact" variant="outlined" :rules="nameRules"
                required></v-text-field>
            </v-col>
   
            <v-col cols="12" md="4">
              <div class="text-subtitle-1 text-medium-emphasis">Surname</div>
   
              <v-text-field v-model="customer.sur_name" :rules="genericRules" density="compact" variant="outlined"
                required></v-text-field>
            </v-col>
   
            <v-col cols="12" md="4">
              <div class="text-subtitle-1 text-medium-emphasis">ID Number</div>
   
              <v-text-field v-model="customer.id_number" :rules="genericRules" density="compact" variant="outlined"
                required></v-text-field>
            </v-col>
   
            <v-col cols="12" md="4">
   
              <div class="text-subtitle-1 text-medium-emphasis">KRA PIN</div>
              <v-text-field v-model="customer.kra_pin_number" :rules="genericRules" density="compact" variant="outlined"
                required></v-text-field>
            </v-col>
          </v-row>
        </v-container>
        <v-container v-if="currentForm == 2">
          <v-row>
            <v-col cols="12" md="4">
              <h4>Contact Information</h4>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12" md="4">
   
              <div class="text-subtitle-1 text-medium-emphasis">Phone Number</div>
              <v-text-field v-model="customer.phone_number" :rules="genericRules" density="compact" variant="outlined"
                required></v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </v-form>
    </v-card-item>
   
      <v-card-actions>
        <v-btn color="blue" rounded v-if="currentForm > 1" @click="currentForm--">
          Previous
        </v-btn>
        <v-btn color="blue" rounded @click="moveNext()">
          Next
        </v-btn>
      </v-card-actions>
    </v-card>
   
  </template>
   
  <script setup>
  import { ref } from "vue"
  const valid = ref(false)
   
  let currentForm = ref(1)
   
  let customer = ref({
    first_name: ref(''),
    middle_name: ref(''),
    sur_name: ref(''),
    id_number: ref(''),
    kra_pin_number: ref(''),
    phone_number: ref('')
   
  })
  const genericRules = [
    val => {
      if (val) return true
      return 'Name is required.'
    },
  ]
  const nameRules = [
    value => {
      if (value) return true
   
      return 'Name is required.'
    },
    value => {
      if (value?.length <= 10) return true
   
      return 'Name must be less than 10 characters.'
    },
  ]
  const email = ref('')
  const emailRules = [
    value => {
      if (value) return true
   
      return 'E-mail is required.'
    },
    value => {
      if (/.+@.+\..+/.test(value)) return true
   
      return 'E-mail must be valid.'
    },
  ]
   
  function moveNext() {
    if (true) {
      doBackgroundCheck()
    } else {
      // currentForm++
    }
  }
  function doBackgroundCheck() {
    //Called endpoint and determined we can move forward..
    if (true) {
      currentForm.value++
    }
  }
  </script>
   
  <style lang="scss" scoped></style>