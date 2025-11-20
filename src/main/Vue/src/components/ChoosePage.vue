<template>
  <div class="choose-character-page">
    <h2 class="left">瘋狂Jamesの致富之路</h2>
    <h1>Choose Your Character</h1>
    <div v-if="loading" class="loading">Loading characters...</div>
    <div v-if="error" class="error">Error loading characters. Please try again.</div>
    <div class="character-options">
      <div class="Character1">
        <img class="image" src="/image/knight.png" alt="knight" />
        <h3>knight</h3>
        <div class="buttonset" @click.stop="selectCharacter('knight')" 
             :disabled="loading">
          {{ loading ? 'loading' : 'Select' }}
        </div>
      </div>
      <div class="Character2">
        <img class="image" src="/image/villager.png" alt="villager" />
        <h3>Villager</h3>
        <div class="buttonset" @click.stop="selectCharacter('villager')" 
             :disabled="loading">
          {{ loading ? 'loading' : 'Select' }}
        </div>
      </div>
      <div class="Character3">
        <img class="image" src="/image/wizard.png" alt="wizard" />
        <h3>Wizard</h3>
        <div class="buttonset" @click.stop="selectCharacter('wizard')" 
             :disabled="loading">
          {{ loading ? 'loading' : 'Select' }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000
});

export default {
  name: "ChoosePage",
  data() {
    return {
      loading: false,
      error: null
    };
  },
  methods: {
    async selectCharacter(character) {
      console.log("Selected character:", character);
      
      this.loading = true;
      this.error = null;
      
      try {
        const response = await api.post(`/player/${character}`);
        console.log('Character selection response:', response.data);
        this.$router.push('/ShopPage');
        
      } catch (err) {
        console.error('Error selecting character:', err);
      } finally {
        this.loading = false;
      }
    },
  }
};
</script>

<style> 
  html, body {
    margin: 0;
    padding: 0;
    height: 100%;
    background-color: black !important;   /* 強制黑色 */
  }
  .image{
    width: 200px;
    height: 200px;
  }
  .choose-character-page {
    position: fixed;
    top: 80px;
    left: 0;
    right: 0;
    bottom: 0;
    text-align: center;
    background-color: black;
    color: white;
    padding-top: 20px;
  }
  .character-options{
    margin: 40px 0 0 40px;
    justify-content: center;
    align-items: center;
    position: relative;
    display: flex;
    gap:100px;
  }
  .buttonset {
    padding: 5px 20px;
    white-space: nowrap;
    display: inline-block;
    background-color: darkgray;
    border-radius: 25px;
    color: #FF0000;
  }
  .Character1, .Character2, .Character3 {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .Character1{
    position: relative;
    margin-left: 200px;
  }
  .Character2{
    position: relative;
  }
  .Character3{
    position: relative;
    margin-right: 200px;
  }
  .left{
    position: fixed;
    top: 0;
    left: 0;
    width: auto;
    height: 70px;
    background-color: black;
    display: flex;
    align-items: center;
    padding-left: 30px; 
    z-index: 9999;
  }
</style>