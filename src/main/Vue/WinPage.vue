<template>
    <div class="EndPage">
        <h1>瘋狂Jamesの致富之路</h1>
        <p>You win</p>
        <p>Thank you for playing</p>
        <router-link to="/choose-characterpage">
            <button>Restart Game</button>
        </router-link>
    </div>
</template>

<script>
export default {
    name: "EndPage",
};
</script>

<style> 
.EndPage {
    position: fixed;
    top:0;
    left:0;
    width:100%;
    height:100%;
    text-align: center;
    background-color:black;
    color: white;
}
button {
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
}
</style>