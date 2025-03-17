<template>
  <v-layout>
    <v-navigation-drawer v-model="drawer" :rail="rail" permanent @click="rail = false" color="green-darken-3">
      <v-list-item title="CO-OP BANK" nav>
        <template v-slot:append>
          <v-btn :icon="rail ? 'mdi-chevron-right' : 'mdi-chevron-left'" variant="text"
            @click.stop="rail = !rail"></v-btn>
        </template>
      </v-list-item>

      <v-divider></v-divider>

      <v-list v-model:opened="open">
        <template v-for="item, id in listItems" :key="id">
          <v-list-group :value="item.title">
            <template v-slot:activator="{ props }">
              <v-list-item :prepend-icon="item.icon" v-bind="props" :title="item.title"></v-list-item>
            </template>
            <v-list-item v-for="child, id in item.children" style="background-color: #1B5E20" :to='child.route' :key="id" :title="child.title" :prepend-icon="child.icon">

            </v-list-item>
          </v-list-group>
        </template>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar color="green-lighten-1">
      <v-spacer></v-spacer>
      <v-avatar>
        <v-img src="https://randomuser.me/api/portraits/men/85.jpg"></v-img>
      </v-avatar>
    </v-app-bar>
    <v-main>
      <router-view />
    </v-main>
  </v-layout>
</template>

<script setup>
import {ref} from 'vue'
const drawer = ref(true)
const rail = ref(false)
const open = ref(['Adminstrative'])
const listItems = ref(
  [{title: "Adminstrative", icon:'mdi-account', children: [{title: 'Single account', color: 'green-darken-4', route: 'basicInfo', icon:'mdi-account'},
  {title: 'Joint account', color: 'green-darken-4', route: 'jointregister', icon:'mdi-account-group'}]
},
{title: "User Management",icon:'mdi-account-group', children: [{title: 'Add/Edit User', color: 'green-darken-4', route: 'adduser', icon:'mdi-account'},
  {title: 'Delete User', color: 'green-darken-4', route: 'deleteuser', icon:'mdi-account-group'}]
}])
</script>

<style>

</style>


