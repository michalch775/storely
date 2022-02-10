import styles from '../styles/Items.module.scss';
import MediumWidget from "../containers/MediumWidget";
import Search from "../components/Search";
import SortSwitch from "../components/SortSwitch";
import Filter from "../components/Filter";
import ItemsTableCel from "../containers/ItemsTableCell";

function Items() {



  return (
      <div className={styles.main}>
          <div className={styles.widgets}>
              <MediumWidget number={254}>Wypożyczenia dziś</MediumWidget>
              <MediumWidget number={1024}>Pobrania dziś</MediumWidget>
              <MediumWidget number={5555}>Zwroty dziś</MediumWidget>
              <MediumWidget number={5555}>Nowych przedmiotów dziś</MediumWidget>
          </div>
          <div className={styles.list}>
              <div className={styles.listHeader}>
                  <h2>Przedmioty w magazynie</h2>
                  <div className={styles.listHeaderFlex}>
                      <Search/>
                      <SortSwitch/>
                      <Filter/>
                  </div>
              </div>

              <table>
                  <thead>
                  <tr>
                      <th>Nazwa</th>
                      <th>Ilość</th>
                      <th>Średnia tygodniowa wydań</th>
                      <th>Kategoria</th>
                      <th>Rodzaj</th>
                  </tr>
                  </thead>
                  <tbody>
                  <ItemsTableCel>
                      test
                  </ItemsTableCel>
                  </tbody>
              </table>


          </div>
      </div>

  );
}

export default Items;
