import styles from '../../styles/Shortages.module.scss';
import MediumWidget from '../../items/MediumWidget';
import SortSwitch from '../../components/SortSwitch';
import SearchComponent from '../../components/Search/SearchComponent';
import Filter from '../../components/Filter';
import ShortageTableCell from '../../items/ShortageTableCell';

function Shortages() {



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
            <h2>Przedmioty poniżej poziomu minimalnego</h2>
            <div className={styles.listHeaderFlex}>
              {/*<SearchComponent/>*/}
              <SortSwitch/>
              <Filter/>
            </div>
          </div>

          <table>
            <thead>
              <tr>
                <th>Nazwa</th>
                <th>Ilość / Poziom minimalny</th>
                <th>Średnia tygodniowa wypożyczeń</th>
                <th>Pokrycie ilości minimalnej</th>
              </tr>
            </thead>
            <tbody>
                <ShortageTableCell>
                  test
                </ShortageTableCell>
            </tbody>
          </table>


        </div>
      </div>

  );
}

export default Shortages;
