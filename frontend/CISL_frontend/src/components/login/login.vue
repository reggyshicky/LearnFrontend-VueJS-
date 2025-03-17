<template>
    <v-card class="mx-auto my-8" elevation="16" max-width="344" style="background-color: aliceblue">
        <v-card-item>
            <v-card-title>
                Please login!
            </v-card-title>
        </v-card-item>

        <v-card-text>
            <v-sheet class="mx-auto" width="300">
                <v-form fast-fail @submit.prevent="routerFunction()">
                    <v-text-field prepend-icon="mdi-account" rounded variant="outlined" v-model="firstName" :rules="firstNameRules" label="First name"></v-text-field>

                    <v-text-field prepend-icon="mdi-eye-off" rounded variant="outlined" v-model="lastName" :rules="lastNameRules" label="Last name"></v-text-field>

                    <v-btn class="mt-2" type="submit" block>Submit</v-btn>
                </v-form>
            </v-sheet>
        </v-card-text>
    </v-card>
</template>

<script setup>
  import { ref } from 'vue'
  import  { useRouter } from 'vue-router'
  const router = useRouter()

  const firstName = ref('')
  const firstNameRules = [
    value => {
      if (value?.length >= 3) return true
      return 'First name must be at least 3 characters.'
    },
  ]

  const lastName = ref('123')
  const lastNameRules = [
    value => {
      if (/[^0-9]/.test(value)) return true
      return 'Last name can not contain digits.'
    },
  ]

  const routerFunction = () => {
    router.replace("/home")
  }
</script>