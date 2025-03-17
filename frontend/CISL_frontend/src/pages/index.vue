<route lang="yaml">
meta:
layout: loginLayout
</route>
<template>
  <div class="background-container">
    <img :src="coopImage" alt="coopImage" class="background-image">
    <div class="overlay">
      <login />
    </div>
  </div>
</template>

<script setup>
import coopImage from '@/assets/coop.jpg';
</script>

<style scoped>
.background-container {
  position: relative;
  height: 100vh; /* Full viewport height */
  width: 100%;
  overflow: hidden;
}

.background-image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* Ensures the image covers the entire div */
}

.overlay {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); /* Center the login */
  background: rgba(255, 255, 255, 0.8); /* Light background for visibility */
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
}
</style>
