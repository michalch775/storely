import styles from "../styles/ShortageTableCell.module.scss";
import React, {FC} from 'react';

const ItemsTableCel: React.FC<{children:any}>=({children, ...props})=> (
    <tr className={styles.main}>
        <td>Woda Cisowianka niegazowana</td>
        <td>42</td>
        <td>21</td>
        <td>Napoje</td>
        <td>zwrotne</td>

    </tr>
);


export default ItemsTableCel;
