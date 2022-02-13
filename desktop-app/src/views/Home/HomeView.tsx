import MediumWidget from '../../items/MediumWidget';
import ShortageTableCell from '../../items/ShortageTableCell';
import React, {useEffect, useRef} from 'react';
import HomeChartComponent from "./HomeChartComponent";

import styles from '../../styles/Home.module.scss';

function Home() {


    useEffect(() => {

    },[]);



    function renderItem(){
        return(
            <tr className={styles.main}>
                <td>Woda Cisowianka niegazowana</td>
                <td>42  /  50</td>
                <td>21</td>
                <td className={styles.barTd}>
                    <div className={styles.flex}>
                        <div>84%</div>
                        <div className={styles.bar}>
                            <div className={styles.barRed}
                                 style={{width:84}}/>
                        </div>
                    </div>
                </td>
            </tr>
        );
    }


    return (
        <div className={styles.main}>
            <div className={styles.widgets}>
                <MediumWidget number={254}>Wypożyczenia dziś</MediumWidget>
                <MediumWidget number={1024}>Pobrania dziś</MediumWidget>
                <MediumWidget number={5555}>Zwroty dziś</MediumWidget>
                <MediumWidget number={5555}>Nowych przedmiotów dziś</MediumWidget>
            </div>
            <div className={styles.dashboard}>
                <div className={styles.chart}>
                    <h3>Wypożyczenia w ciągu<br/> ostatnich 30 dni</h3>
                    <div>
                        <HomeChartComponent/>
                    </div>
                </div>
                <div className={styles.chart}>
                    <h3>Pobrania w ciągu<br/> ostatnich 30 dni</h3>
                    <div>
                        <HomeChartComponent/>
                    </div>
                </div>
                <div className={styles.list}>
                    <h2>Deficyt</h2>

                    <table>
                        <thead>
                        <tr>
                            <th>PRZEDMIOT</th>
                            <th>ILOŚĆ/POZIOM MINIMALNY</th>
                            <th>ŚREDNIA TYGODNIOWA WYPOŻYCZEŃ</th>
                            <th>POKRYCIE ILOŚCI MINIMALNEJ</th>
                        </tr>
                        </thead>
                        <tbody>
                            {renderItem()}
                        </tbody>
                    </table>
                </div>
            </div>

            <div>

            </div>
        </div>

    );
}

export default Home;
