/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilestoremanagement;

import models.Mobile;
import models.Users;

/**
 *
 * @author Rutul
 */

    public interface ControllerClass {
        
    public abstract void preloadData(Mobile mobile);
    public abstract void preloadDataUser(Users user);
}

