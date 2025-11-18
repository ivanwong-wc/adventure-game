<template>
  <div class="EndPage">
    <h1>瘋狂Jamesの致富之路</h1>
    <div class="victory-message">
      <p>You lost</p>
      <p>Thank you for playing</p>
    </div>
    
    <div class="buttons">
      <router-link to="/choose-characterpage">
        <button class="restart-btn">Restart Game</button>
      </router-link>
      <button class="leave-btn" @click="leaveGame">Leave Game</button>
    </div>
  </div>
</template>

<script>
export default {
    name: "EndPage",
    methods: {
        leaveGame() {
            if (window.electronAPI) {
                window.electronAPI.quitApp();
            } else {
                window.close();
            }
        }
    }
};
</script>

<style> 
.EndPage {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    text-align: center;
    background: linear-gradient(135deg, #000, #1a1a2e, #16213e);
    color: #FFD700;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-family: 'Arial', sans-serif;
}

.victory-message {
    margin-bottom: 50px;
    animation: glow 2s ease-in-out infinite alternate;
}

@keyframes glow {
    from { text-shadow: 0 0 10px #FFD700; }
    to { text-shadow: 0 0 30px #FFD700, 0 0 40px #FF4444; }
}

.victory-message h2 {
    font-size: 48px;
    margin: 0 0 20px;
    color: #FFD700;
}

.victory-message p {
    font-size: 24px;
    margin: 10px 0;
    color: #fff;
}

.buttons {
    display: flex;
    gap: 30px;
    flex-wrap: wrap;
    justify-content: center;
}

button {
    padding: 18px 40px;
    font-size: 20px;
    border: none;
    border-radius: 50px;
    cursor: pointer;
    font-weight: bold;
    transition: all 0.3s;
    box-shadow: 0 8px 20px rgba(0,0,0,0.3);
}

.restart-btn {
    background: linear-gradient(45deg, #00FF88, #00CC66);
    color: #000;
}

.restart-btn:hover {
    transform: translateY(-5px);
    box-shadow: 0 15px 30px rgba(0,255,136,0.4);
}

.leave-btn {
    background: linear-gradient(45deg, #FF4444, #CC3333);
    color: white;
}

.leave-btn:hover {
    transform: translateY(-5px);
    box-shadow: 0 15px 30px rgba(255,68,68,0.5);
}

@media (max-width: 600px) {
    .buttons { flex-direction: column; gap: 20px; }
    .victory-message h2 { font-size: 36px; }
}
</style>