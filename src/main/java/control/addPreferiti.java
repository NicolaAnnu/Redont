package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Preferiti;
import model.PreferitiDAO;
import model.Utente;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(value = "/addPreferiti", name = "addPreferiti")
public class addPreferiti extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idProdotto;
        try {
            idProdotto = Integer.parseInt(req.getParameter("id"));
        } catch (Exception e) {
            resp.sendError(400, "Id non numerico");
            return;
        }
        PreferitiDAO preferitiDAO = new PreferitiDAO();
        Utente utente = (Utente) req.getSession().getAttribute("utente");
        if (utente == null) {
            resp.sendError(403, "Utente non registrato");
            return;
        }
        int idUtente = utente.getId();
        ArrayList<Preferiti> preferiti = preferitiDAO.doRetrieveByUser(idUtente);
        for (Preferiti preferito : preferiti) {
            if (preferito.getIdProdotto() == idProdotto) {
                preferitiDAO.doDelete(idUtente, idProdotto);
                return;
            }
        }
        preferitiDAO.doSave(idUtente, idProdotto);
    }
}
