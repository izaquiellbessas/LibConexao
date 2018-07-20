/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao.ssh;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author izaquiellopesdebessas
 */
public class SSH2 {

    private Connection conn;
    private boolean isAuthenticated;

    /**
     * Realiza a conexão via SSH2
     *
     * @param hostname nome da máquina/host destino
     * @param username nome de usuário SSH
     * @param password senha do usuário SSH
     * @throws IOException
     */
    public SSH2(String hostname, String username, String password) throws IOException {
        /*
         * Create a connection instance
         */
        this.conn = new Connection(hostname);
        this.conn.connect();

        /*
         * Authenticate. If you get an IOException saying something like
         * "Authentication method password not supported by the server at this
         * stage." then please check the FAQ.
         */
        this.isAuthenticated = conn.authenticateWithPassword(username, password);
        int cont = 0;
        while (!isAuthenticated && cont > 2) {
            System.out.println("Autenticação mal sucedida, tentativa " + ++cont + " de 3");
            this.isAuthenticated = conn.authenticateWithPassword(username, password);
        }
    }

    /**
     * Executa um determinado comando remotamente, via SSH2, na máquina cuja
     * conexão foi estabelecida
     *
     * @param command comando a ser executado no destino
     * @throws IOException
     */
    public void CommandSSH2(String command) throws IOException {
        Session sess = conn.openSession();

        sess.execCommand(command); //"uname -a && date && uptime && who"
        System.out.println("Here is some information about the remote host:");

        /*
         * This basic example does not handle stderr, which is sometimes
         * dangerous (please read the FAQ).
         */
        InputStream stdout = new StreamGobbler(sess.getStdout());
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            System.out.println(line);
        }
        /*
         * Show exit status, if available (otherwise "null")
         */
        System.out.println("ExitCode: " + sess.getExitStatus());

        sess.close();
    }

    /**
     * Encerrar uma conexão SSH já estabelecida
     */
    public void terminated() {
        this.conn.close();
    }
}
