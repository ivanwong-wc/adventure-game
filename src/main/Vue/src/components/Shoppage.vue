<template>
    <div class="top">
        <h2>瘋狂Jamesの致富之路</h2>
    </div>
    <div class="Shop-page">
        <div class="image-wrapper">
            <img class="image" src="/Image/merchant.png" alt="商人" />
            <div class="smallbox">
                {{ message }}
            </div>
            <p>Merchant</p>
        </div>
        <div class="Playeractionbox">
            <div class="Playerstatus">
                <p>You</p>
                <p>HP: {{ playerHP }}</p>
                <p>Mp: {{ playerMP }}</p>
                <p>Gold: {{ playerGold }}</p>
            </div>
            <div class="Shoplist Listshow2">
                <div class="item-scroll-container">
                    <div v-for="(price, item) in Itemlist" :key="item">
                        <div @click="Buyitem(item)">{{ item }} ({{ price }})</div>
                    </div>
                    <div @click="leaveshop">Leave the shop</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
const api = axios.create({
    baseURL: 'http://localhost:8080/api',
});
export default {
    name: "ShopPage",
    data() {
        return {
            // player
            playerHP: 0,
            playerMP: 0,
            playerGold: 0,
            // list
            Itemlist: {},
            message: 'Welcome to the shop! What do you want to buy?',
            getmessage: '',
        };
    },
    methods: {
        async GetPlayerdata() {
            try {
                const response = await api.get(`/player/getCharacter`);
                this.playerHP = response.data.hp ?? 0;
                this.playerMP = response.data.mp ?? 0;
                this.playerGold = response.data.gold ?? 0;
            } catch (err) {
                console.error('Error fetching player data:', err);
            }
        },
        async ShopShow() {
            console.error('fetching shop data:');
            try {
                const response = await api.get(`/shop`);
                this.Itemlist = response.data || {}; 
            } catch (err) {
                console.error('Error fetching shop data:', err);
            }
        },
        async Buyitem(item) {
            console.log("Chosen item/skill:", item);
            try {
                const response = await api.post(`/shop/${item}`);
                this.playerHP = response.data.hp;
                this.playerMP = response.data.mp;
                this.playerGold = response.data.gold;
                this.Itemlist = response.data || {}; 
                this.getmessage = response.data.message;
                if(this.getmessage){
                    this.message = this.getmessage;
                } else {
                    this.message = 'Buy ',item,' successfully!';
                }
            } catch (err) {
                console.error('Error fetching item data:', err);
                this.message = 'Buy failed. Please buy again.';
            }
        },
        leaveshop(){
            this.$router.push("/EventPage");
        }
    },
    mounted() {
        this.GetPlayerdata();
        this.ShopShow();
    },
};
</script>

<style> 
    .body {
        background-color:black;
        margin: 0;
        padding: 0;
        height: 100%;
    }
    .top{
        margin-top: -10px;
        display: flex;
        justify-content: space-between;
        flex-direction: row;
        align-items:center;
        padding: 0px 16px 0px;
        left:auto;
    }  
    .topright{
        position: relative;
        display: flex;
        align-items: center;
        gap: 10px;
        height: 30px;
    }
    .Playeractionbox{
        border: 5px solid white;
        height: 270px;
        margin: auto;
        width:80%;
        display: flex;
    }
    .Shop-page {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-bottom: 20px;
    }

    .image-wrapper {
        position: relative;
        display: inline-block;
        margin-bottom: 10px;
    }

    .smallbox{
        position: absolute;
        top: 10px;
        right: -180px;
        width: 160px;
        padding: 10px;
        background-color: rgba(255, 255, 255, 0.95);
        border: 3px solid white;
        border-radius: 15px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
        color: #333;
        font-size: 14px;
        text-align: center;
        z-index: 10;
    }

    .image{
        width: 200px;
        height: 200px;
    }
    .Playerstatus{
        float: left;
        width: 40%;
        height: 100%;
        text-align: left;
        padding-left: 20px;
    }
    .Shoplist{
        float: right;
        width: 50%;
        height: 100%;
        gap: 20px;
        text-align: left;
        padding-left: 20px;
    }
    .appear{
        display:block;
    }
    .disappear{
        display:none;
    }
    .item-scroll-container {
        max-height: 200px;
        width: 100%;
        overflow-y: auto;
        padding-right: 8px;
        margin-bottom: 10px;
    }
    .loop {
        margin: 8px 0;
    }
    .back-btn {
        margin-top: 10px;
        background-color: #555;
        color: white;
    }

    .item-scroll-container::-webkit-scrollbar {
        width: 8px;
    }

    .item-scroll-container::-webkit-scrollbar-track {
        background: #333;
        border-radius: 4px;
    }

    .item-scroll-container::-webkit-scrollbar-thumb {
        background: #888;
        border-radius: 4px;
    }

    .item-scroll-container::-webkit-scrollbar-thumb:hover {
        background: #aaa;
    }

</style>