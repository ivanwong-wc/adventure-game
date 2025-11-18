<template>
  <div class="top">
    <h2>瘋狂Jamesの致富之路</h2>
    <div class="topright" @click="gotosetting()"><h2>Setting</h2></div>
  </div>
  <div class="choose-character-page">
    <h1>Choose Your Character</h1>
    <div v-if="loading" class="loading">Loading characters...</div>
    <div v-if="error" class="error">Error loading characters. Please try again.</div>
    <div class="character-options">
      <div class="Character1">
        <!--<img role="text" :aria-label="$t('info')" src="@/assets/images/home-page/info.svg"></img>-->
        <h3>knight</h3>
        <div class="buttonset" @click.stop="selectCharacter('knight')" 
             :disabled="loading">
          {{ loading ? 'loading' : 'Select' }}
        </div>
      </div>
      <div class="Character2">
        <!--<img role="text" :aria-label="$t('info')" src="@/assets/images/home-page/info.svg"></img>-->
        <h3>Villager</h3>
        <div class="buttonset" @click.stop="selectCharacter('villager')" 
             :disabled="loading">
          {{ loading ? 'loading' : 'Select' }}
        </div>
      </div>
      <div class="Character3">
        <!--<img role="text" :aria-label="$t('info')" src="@/assets/images/home-page/info.svg"></img>-->
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
  baseURL: 'http://localhost:8081/api',
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
        this.$router.push('/Shoppage');
        
      } catch (error) {
        console.error('Error selecting character:', error);
      } finally {
        this.loading = false;
      }
    },
    
    gotosetting() {
      this.$router.push("/setting-page");
    }
  }
};
</script>

<style> 
  .body {
    background-color:black;
    margin: 0;
    padding: 0;
    height: 100%;
  }
  .choose-character-page{
    position: fixed;
    top:50px;
    left:0;
    width:100%;
    height:100%;
    text-align: center;
    background-color:black;
    color: white;
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
  .top{
    margin-top: -10px;
    display: flex;
    justify-content: space-between;
    flex-direction: row;
    align-items:center;
    padding: 0px 16px 0px;
    right:0;
    left:auto;
  }
  .topright{
      position: relative;
      display: flex;
      align-items: center;
      gap: 10px;
      height: 30px;
  }
</style>