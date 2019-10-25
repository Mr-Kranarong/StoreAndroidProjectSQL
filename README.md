# StoreAndroidProjectSQL
This is my real crappy android project, expect nothing.

:white_check_mark: 09/25/2019 - Load item in main menu.

:white_check_mark: 09/26/2019 - Fragmentation & Navigation Drawer.

:white_check_mark: 09/27/2019 - Registration works.

:white_check_mark: 09/28/2019 - Login, Session, & All Authentication Aspect is now functional.

:white_check_mark: 09/29/2019 - Profile Info Edit & Fund Management is done.

:white_check_mark: 10/21/2019 - Everything is working, the project is done.

<table>
  <tr><td colspan="2"><b>Features & Craps</b></td></tr>
<tr>
  <td>Market Page</td>
  <td>- Show all items with count more than 0 & will not show your own listed items.<br>
    - Click store name will display Store cover image & description.<br>
    - Add to Cart will bring popup dialog for inputing number, if already exists in cart, number will increase cumulatively.<br>
    - Click store name will display Store cover image & description.<br>
    - Can display both static & gif item images.
  </td>
</tr>
<tr>
  <td>Your Store Page</td>
  <td>- If not yet create store, it show page for upload pic, add store name and description.<br>
    - If already created, will show store management page which allow you to proceed to edit store information, listing items, edit items, or delete items.
  </td>
</tr>
<tr>
  <td>Transaction Page</td>
  <td>- If you bought items, buy reocrd will be shown here, or if some other user bought your items, sale record will be shown here as well.<br>
    - Log record information included, timestamp, count, price of item, total cost.
  </td>
</tr>
<tr>
  <td>Cart Page</td>
  <td>- Will list all items you added to cart from Market Page.<br>
    - This page allows you to clear cart, checkout, change count of item in cart, and remove from cart.
  </td>
</tr>
<tr>
  <td>Profile Page</td>
  <td>- Let you edit your personal information and password.<br>
    - When you press back without clicking update, all changes will be reverted automatically.<br>
    - This page have fund management button which will bring popup asking how much to deposit or withdraw. (simulated number)
  </td>
</tr>
<tr>
  <td>Login Page</td>
  <td>- Fields for username and password, and a label to proceed to register page.<br>
    - Register page let you fill your informations and send you back here once registration done.<br>
  </td>
</tr>
<tr>
  <td>Backend Functionality</td>
  <td>- PHP will replace picture name with 100 lengths long string will prevent overlapping before upload, store image url in sql.<br>
    - Using share-preferences to store AUTH-KEY come from randomly generated string from login which has been added to AUTHEN sql table. In this way, new session of same user will destroy the old one.<br>
    - Users who are not logged in will only see Market & Login page. Add to Cart button will be hidden.
  </td>
</tr>
</table>

<table>
  <tr><td colspan="2"><b>Preview Images</b></td></tr>
<tr>
  <td><img src="https://raw.githubusercontent.com/Mr-Kranarong/StoreProjectSQL/master/pic1.jpg"/></td>
  <td><img src="https://raw.githubusercontent.com/Mr-Kranarong/StoreProjectSQL/master/pic2.jpg"/></td>
</tr>
</table>
<i>*Font not included.</i>
