import styles from "../styles/Login.module.scss";

function Login() {
  return (
      <div className={styles.loginBox}>
        <form>
          <p>Zaloguj się do systemu</p>
          <input type="text" placeholder="Adres Email"/>
          <input type="password" placeholder="Hasło"/>
          <button>Zaloguj</button>
        </form>
      </div>
  );
}

export default Login;
