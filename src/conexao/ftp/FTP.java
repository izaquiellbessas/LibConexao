/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author izaquiellopesdebessas
 */
public class FTP {

    private FTPClient ftp;

    /**
     * Construtor padrão da classe
     */
    public FTP() {
    }

    /**
     * Construtor padrão da classe
     *
     * @param port porta para conexão FTP
     * @param localport porta local
     * @param hostname nome da máquina/host para conexão
     * @param ip endereço IP da conexão
     * @param username usuário FTP
     * @param password senha do usuário FTP
     * @throws UnknownHostException
     * @throws SocketException
     * @throws IOException
     */
    public FTP(int port, int localport, String hostname, String ip, String username, String password) throws UnknownHostException, SocketException, IOException {
        this.ftp = new FTPClient();
        InetAddress ia = InetAddress.getByName(ip);

        this.ftp.connect(hostname, port, ia, localport);
        if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            ftp.login(username, password);
        } else {
            ftp.disconnect();
        }
    }

    /**
     * Conexão FTP somente com hostname
     *
     * @param port porta para conexão FTP
     * @param localport porta local
     * @param hostname nome da máquina/host para conexão
     * @param username usuário FTP
     * @param password senha do usuário FTP
     * @throws SocketException
     * @throws IOException
     */
    public void FTPjustHostname(int port, int localport, String hostname, String username, String password) throws SocketException, IOException {
        this.ftp = new FTPClient();

        this.ftp.connect(hostname, port);
        if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            ftp.login(username, password);
        } else {
            ftp.disconnect();
        }
    }

    /**
     * Conexão FTP somente com endereço IP
     *
     * @param port porta para conexão FTP
     * @param localport porta local
     * @param ip endereço IP para conexão
     * @param username usuário FTP
     * @param password senha do usuário FTP
     * @throws UnknownHostException
     * @throws SocketException
     * @throws IOException
     */
    public void FTPjustIPAddress(int port, int localport, String ip, String username, String password) throws UnknownHostException, SocketException, IOException {
        this.ftp = new FTPClient();
        InetAddress ia = InetAddress.getByName(ip);

        this.ftp.connect(ia, port, ia, localport);
        if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            ftp.login(username, password);
        } else {
            ftp.disconnect();
        }
    }

    /**
     * Enviar arquivos a um destino com conexão previamente já estabelecida
     *
     * @param listFiles lista de arquivos a serem enviados (vetor de string, os
     * quais contém a url de cada arquivo)
     * @throws UnknownHostException
     * @throws SocketException
     * @throws IOException
     */
    public void sendFiles(String[] listFiles) throws UnknownHostException, SocketException, IOException {
        //para cada arquivo informado
        for (String listFile : listFiles) {
            //abre um stream com o arquivo a ser enviado
            InputStream is = new FileInputStream(listFile);
            //pega apenas o nome do arquivo
            int idx = listFile.lastIndexOf(File.separator);
            if (idx < 0) {
                idx = 0;
            } else {
                idx++;
            }
            String fileName = listFile.substring(idx, listFile.length());
            //ajusta o tipo do arquivo a ser enviado
            if (listFile.endsWith(".txt") || (listFile.endsWith(".html")) || (listFile.endsWith(".xhtml")) || (listFile.endsWith(".css")) || (listFile.endsWith(".htm")) || (listFile.endsWith(".php")) || (listFile.endsWith(".forward")) || (listFile.endsWith(".htaccess")) || (listFile.endsWith(".ctl")) || (listFile.endsWith(".pwd")) || (listFile.endsWith(".map")) || (listFile.endsWith(".js")) || (listFile.endsWith(".cgi")) || (listFile.endsWith(".pl")) || (listFile.endsWith(".ini")) || (listFile.endsWith(".cnf")) || (listFile.endsWith(".conf"))) {
                ftp.setFileType(FTPClient.ASCII_FILE_TYPE);
            } else {
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            }
            //faz o envio do arquivo
            ftp.storeFile(fileName, is);
        }
    }

    /**
     * Disconectar conexão FTP estabelecida
     *
     * @throws IOException
     */
    public void disconect() throws IOException {
        ftp.disconnect();
    }
}
