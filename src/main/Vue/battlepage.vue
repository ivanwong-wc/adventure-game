<template>
    <div class="top">
        <h2>瘋狂Jamesの致富之路</h2>
        <!--<div class="topright" @click="gotosetting()"><h2>Setting</h2></div>-->
    </div>
    <div class="Battle-page">
        <div class="EnemyLook">
            <img role="text" :aria-label="$t('info')" src="@/main/Image/ememy.jpeg"></img>
            <p>Enemy</p>
            <p>HP: {{ enemyHP }}</p>
        </div>
        <div class="Playeractionbox">
            <div class="Playerstatus">
                <p>You</p>
                <p>HP: {{ playerHP }}</p>
                <p>Mp: {{ playerMP }}</p>
                <p>Buff: {{ playerBuff }}</p>
                <p>Luck: {{ playerLuck }}</p>
            </div>
            <div class="Playeraction actionbuttons" v-if="!ListOpen1 && !ListOpen2">
                <div class="buttonset" @click="attack(mainweaponattack)">Attack</div>
                <div class="buttonset" @click="showskill()">Skills</div>
                <div class="buttonset" @click="showitem()">Items</div>
            </div>
            <div class="Playeraction Listshow1" v-if="ListOpen1" v-for="item in list">
                <div class="buttonset" @click="attack(item)">{{ item }}</div>
                <div class="buttonset" @click="GoBack()">Go Back</div>
            </div>
            <div class="Playeraction Listshow2" v-if="ListOpen2" v-for="item in list">
                <div class="buttonset" @click="choose(item)">{{ item }}</div>
                <div class="buttonset" @click="GoBack()">Go Back</div>
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
            enemyName: '',
            enemyHP: 0,
            enemyTotalHP: 0,
            // player
            playerHP: 0,
            playerTotalHP: 0,
            playerMP: 0,
            playerTotalMP: 0,
            playerBuff: '',
            playerLuck: 0,
            mainweaponattack: 0,
            // list
            list: [],
            Itemlist: [],
            skilllist: [],
            ListOpen1: false,
            ListOpen2: false,
            // round
            playerround: true,
        };
    },
    methods: {
        async createEnemy(level) {
            try {
                const response = await api.post(`/enemy/${level}`);
                //this.enemyName = response.data.name;
                this.enemyHP = response.data.currentHP;
                //this.enemyTotalHP = response.data.totalHP;
            } catch (error) {
                console.error('Error fetching enemy data:', error);
            }
        },
        async GetPlayerdata() {
            try {
                const response = await api.post(`/player/getCharacter`);
                this.playerHP = response.data.hp ?? 0;
                //this.playerTotalHP = response.data.hp ?? this.playerTotalHP;
                this.playerMP = response.data.mp ?? 0;
                //this.playerTotalMP = response.data.mp ?? this.playerTotalMP;
                this.playerLuck = response.data.luck ?? 0;
                this.playerBuff = response.data.buff ?? 0;
                this.mainweaponattack = response.data.attack ?? 0;
                this.Itemlist = response.data.inventory || [];
                this.skilllist = response.data.skills || [];
            } catch (error) {
                console.error('Error fetching player data:', error);
            }
        },
        gotosetting() {
            this.$router.push("/setting-page");
        },
        async attack(damage) {
            console.log("Attack button clicked, damage:", damage);
            try {
                const response = await api.post(`/attack/${damage}`);
                this.enemyHP = response.data.currentHP;
                this.playerMP = response.data.playerMP;
                this.ListOpen1 = false;
                this.ListOpen2 = false;
                this.playerround = false;
                if(this.enemyHP < 1){
                    console.log("User Win!!!");
                    this.$router.push("/Shoppage");
                }else{
                    this.userunderattack();
                }
            } catch (error) {
                console.error('Error fetching attack data:', error);
            }
        },
        async choose(item) {
            console.log("Chosen item/skill:", item);
            try {
                const response = await api.post(`/items/${item}`);
                this.playerHP = response.data.playerHP;
                this.enemyHP = response.data.enemyHP;
                this.Itemlist = response.data.inventory || [];
                this.playerround = false;
                this.userunderattack();
            } catch (error) {
                console.error('Error fetching item data:', error);
            }
        },
        showskill() {
            console.log("Skill button clicked");
            let skilllistTest = [
                "Fireball",
                "Ice Spike",
                "Heal",
                "Lightning Strike"
            ]
            this.list = skilllist;
            this.ListOpen1 = true;
            this.ListOpen2 = false;
        },
        GoBack() {
            console.log("Go Back button clicked");
            this.ListOpen1 = false;
            this.ListOpen2 = false;
        },
        showitem() {
            console.log("Item button clicked");
            let ItemlistTest = [
                "cold",
                "C4",
                "apple",
                "Java book"
            ]
            this.list = Itemlist;
            this.ListOpen1 = false;
            this.ListOpen2 = true;
        },
        async userunderattack() {
            console.log("Enemy is attacking");
            try{
                const response = await api.post(`/enemyAttack/`);
                this.playerHP = response.data.playerHP;
                this.enemyHP = response.data.enemyHP;
                if(this.playerHP > 1){
                    this.playerround = true;
                }else{
                    this.$router.push("/EndPage");
                }
            } catch (error) {
                console.error('Error fetching enemy attack data:', error);
            }
        }
    },
    mounted() {
        this.GetPlayerdata();
        this.createEnemy(1);
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
    .buttonset {
        padding: 5px 20px;
        white-space: nowrap;
        display: inline-block;
        background-color: darkgray;
        border-radius: 25px;
        color: #FF0000;
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
        width: 40%;
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
</style>