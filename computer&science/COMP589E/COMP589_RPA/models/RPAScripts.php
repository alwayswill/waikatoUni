<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "{{%scripts}}".
 *
 * @property integer $id
 * @property string $name
 * @property string $path
 * @property string $comments
 */
class RPAScripts extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return '{{%scripts}}';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['name', 'path', 'comments'], 'required'],
            [['path', 'comments'], 'string'],
            [['name'], 'string', 'max' => 100]
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'name' => 'Name',
            'path' => 'Path',
            'comments' => 'Comments',
        ];
    }

    /**
     * @inheritdoc
     * @return RPAScriptsQuery the active query used by this AR class.
     */
    public static function find()
    {
        return new RPAScriptsQuery(get_called_class());
    }
}
