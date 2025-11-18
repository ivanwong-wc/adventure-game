<template>
    <div class="top">
        <h2>瘋狂Jamesの致富之路</h2>
        <!--<div class="topright" @click="gotosetting()"><h2>Setting</h2></div>-->
    </div>
    <div class="Battle-page">
        <div class="image-wrapper">
            <img class="image" role="text" :aria-label="$t('info')" src="@/main/Image/ememy.jpeg"></img>
            <div class="smallbox">{{ message.event }}</div>
        </div>
        <div class="Playeractionbox">
            <div class="Playerstatus">
                <p>You</p>
                <p>HP: {{ playerHP }}</p>
                <p>Mp: {{ playerMP }}</p>
                <p>Gold: {{ playerGold }}</p>
                <p>Gold: {{ playerGold }}</p>
                <p>Luck: {{ playerLuck }}</p>
            </div>
            <div class="Playeraction actionbuttons" v-if="!ListOpen2 && ListOpen3">
                <div class="item-scroll-container">
                    <div @click="Give()">Do it</div>
                    <div @click="showitem()">Item</div>
                    <div @click="goaway()">Go away</div>
                </div>
            </div>
            <div class="Playeraction Listshow2" v-if="ListOpen2">
                <div class="item-scroll-container">
                    <div v-for="item in list" :key="item">
                        <div>{{ item }}</div>
                    </div>
                    <div @click="GoBack()">Go Back</div>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
import axios from 'axios';
const api = axios.create({
    baseURL: 'http://localhost:8081/api',
});
export default {
    name: "battlepage",
    data() {
        return {
            // enemy
            enemyHP: 0,
            // player
            playerHP: 0,
            playerMP: 0,
            playerAttack: 0,
            playerLuck: 0,
            getGold: 0,
            playerGold: 0,
            // list
            list: [],
            Itemlist: [],
            ListOpen2: false,
            ListOpen3: true,
            // message
            message: '',
            messagetype: '',
        };
    },
    methods: {
        async GetPlayerdata() {
            try {
                const response = await api.get(`/player/getCharacter`);
                this.playerHP = response.data.hp ?? 0;
                this.playerMP = response.data.mp ?? 0;
                this.playerAttack = response.data.attack ?? 0;
                this.playerLuck = response.data.luck ?? 0;
                this.playerGold = response.data.gold ?? 0;
                this.Itemlist = response.data.inventory || [];
                this.skilllist = Object.keys(response.data.skills || {});
            } catch (error) {
                console.error('Error fetching player data:', error);
            }
        },
        async showevent() {
            try {
                const response = await api.get(`/event`);
                this.message = response.data.event ?? '';
                this.messagetype = response.data.key ?? ''; 
            } catch (error) {
                console.error('Error fetching event data:', error);
            }
        },
        async Give() {
            console.log("Give button clicked");
            try{
                const response = await api.get(`/event/${messagetype}/${'true'}`);
                this.message = response.data.message ?? '';
                this.playerHP = response.data.playerHp ?? 0;
                this.playerAttack = response.data.playerAttack ?? 0;
                this.playerGold = response.data.gold ?? 0;
                this.ListOpen3 = false;
                setTimeout(3000);
                this.$router.push("/battlepage");
            } catch (error) {
                console.error('Error giving item:', error);
            }
        },
        showitem() {
            console.log("Item button clicked");
            this.list = this.Itemlist;
            this.ListOpen2 = true;
        }
    },
    mounted() {
        this.GetPlayerdata();
        this.showevent();
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

    .Battle-page {
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
    .Playeractionbox{
        border: 5px solid white;
        height: 270px;
        width: 80%;
        display: flex;
        margin: auto;
    }
    .Playerstatus{
        float: left;
        width: 40%;
        height: 100%;
        text-align: left;
        padding-left: 20px;
    }
    .Playeraction{
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