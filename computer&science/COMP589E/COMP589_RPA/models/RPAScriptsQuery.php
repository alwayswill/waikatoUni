<?php

namespace app\models;

/**
 * This is the ActiveQuery class for [[RPAScripts]].
 *
 * @see RPAScripts
 */
class RPAScriptsQuery extends \yii\db\ActiveQuery
{
    /*public function active()
    {
        $this->andWhere('[[status]]=1');
        return $this;
    }*/

    /**
     * @inheritdoc
     * @return RPAScripts[]|array
     */
    public function all($db = null)
    {
        return parent::all($db);
    }

    /**
     * @inheritdoc
     * @return RPAScripts|array|null
     */
    public function one($db = null)
    {
        return parent::one($db);
    }
}