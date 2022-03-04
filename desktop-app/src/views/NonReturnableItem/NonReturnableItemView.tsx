/*
 * All Rights Reserved
 *
 * Copyright (c) 2022 Michał Chruścielski
 */

import React from 'react';
import styles from '../../styles/Item.module.scss';

import {NonReturnableItemViewProps} from "./NonReturnableItemViewProps";
import {Group} from "../../api/entities/Group";

function NonReturnableItemView(props:NonReturnableItemViewProps) {

    function renderGroup(element: Group){
        return <div>{element.name}</div>;
    }

    return (
        <div className={styles.main}>
            <div className={styles.sideBar}>
                <div className={styles.stats}>
                    <div className={styles.backgroundHeader}></div>
                    <div className={styles.header}>
                        <h1>{props.item.quantity}</h1>
                        <div>{props.item.itemTemplate.category.name}</div>
                        <div className={styles.buttons}>
                            <button>usun</button>
                            <button>edytuj</button>
                        </div>
                    </div>
                    <h3>Dane przedmiotu</h3>
                    <div className={styles.itemData}>
                        <div>Nazwa</div>
                        <div>{props.item.itemTemplate.name}</div>
                        <hr/>
                        <div>Model</div>
                        <div>{props.item.itemTemplate.model}</div>
                        <hr/>
                        <div>Ilość krytyczna</div>
                        <div>{props.item.itemTemplate.criticalQuantity}</div>
                        <hr/>
                        <div>Ostatnia dostawa</div>
                        <div>{props.item.added}</div>
                        <hr/>
                        <div>Data utworzenia</div>
                        <div>{props.item.itemTemplate.added}</div>
                        <hr/>
                        <div>Rodzaj</div>
                        <div>Bezzwrotne</div>
                    </div>
                    <h3>Opis</h3>
                    <div className={styles.itemData}>
                        {props.item.itemTemplate.description}
                    </div>
                </div>
                <div>
                    <div>Grupy</div>
                    <div>{props.item.itemTemplate.groups.map((x,i)=>renderGroup(x))}</div>
                </div>
            </div>
            <div className={styles.itemList}>
                TODO bede gral w gre
            </div>

        </div>

    );
}

export default NonReturnableItemView;
