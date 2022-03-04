/*
 * All Rights Reserved
 *
 * Copyright (c) 2022 Michał Chruścielski
 */

import MediumWidget from '../../items/MediumWidget';
import React from 'react';
import HomeChartComponent from "./HomeChartComponent";

import styles from '../../styles/Home.module.scss';
import {HomeViewProps} from "./HomeViewProps";

function Home(props:HomeViewProps) {

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
                <MediumWidget number={props.widgets != null ? props.widgets.rentalsToday : null}>Wypożyczenia dziś</MediumWidget>
                <MediumWidget number={props.widgets != null ? props.widgets.retrievalsToday : null}>Pobrania dziś</MediumWidget>
                <MediumWidget number={props.widgets != null ? props.widgets.returnsToday : null}>Zwroty dziś</MediumWidget>
                <MediumWidget number={props.widgets != null ? props.widgets.itemsAddedToday : null}>Nowych przedmiotów dziś</MediumWidget>
            </div>
            <div className={styles.dashboard}>
                <div className={styles.chart}>
                    <h3>Wypożyczenia w ciągu<br/> ostatnich 30 dni</h3>
                    <div>
                        <HomeChartComponent chart={props.rentalChart}/>
                    </div>
                </div>
                <div className={styles.chart}>
                    <h3>Pobrania w ciągu<br/> ostatnich 30 dni</h3>
                    <div>
                        <HomeChartComponent chart={props.retrievalChart}/>
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
